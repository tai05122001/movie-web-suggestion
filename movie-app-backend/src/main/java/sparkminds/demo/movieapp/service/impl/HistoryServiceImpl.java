package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.constant.MessageContant;
import sparkminds.demo.movieapp.entity.History;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.HistoryRepository;
import sparkminds.demo.movieapp.repository.MovieRepository;
import sparkminds.demo.movieapp.repository.UserRepository;
import sparkminds.demo.movieapp.service.HistoryService;
import sparkminds.demo.movieapp.service.dto.request.CreateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.response.HistoryResponseDto;
import sparkminds.demo.movieapp.service.mapper.HistoryMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.MOVIE_IS_NOT_FOUND;
import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.USER_IS_NOT_FOUND;
import static sparkminds.demo.movieapp.constant.MessageContant.MOVIE_IS_NULL;
import static sparkminds.demo.movieapp.constant.MessageContant.USER_IS_NULL;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final HistoryMapper historyMapper;
    private final MovieRepository movieRepository;

    @Override
    public HistoryResponseDto getHistoryByIdUser(UUID idUser) {
        History history = historyRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.HISTORY_IS_NOT_FOUND));
        return historyMapper.toHistoryResponseDto(history);
    }

    @Override
    public List<HistoryResponseDto> getAllHistory() {
        List<History> histories = historyRepository.findAll();
        if (historyRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_HISTORY_IS_NULL);
        }
        List<HistoryResponseDto> result = new ArrayList<>();
        histories.forEach(history -> {
            result.add(historyMapper.toHistoryResponseDto(history));
        });
        return result;
    }

    @Override
    public void addHistory(CreateHistoryRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(()-> new NotFoundException(USER_IS_NOT_FOUND));
        Movie movie = movieRepository.findById(requestDto.getIdMovie())
                .orElseThrow(() -> new NotFoundException(MOVIE_IS_NOT_FOUND));
        History history = History.builder().build();
        history.setIdHistory(UUID.randomUUID());
        history.setUser(user);
        history.setMovie(movie);
        history.setStatus(TypeEntity.ACTIVE);
        history.setViewAt(LocalDateTime.now());
        history.setRating(requestDto.getRating());
        historyRepository.save(history);
    }

    @Override
    public void updateHistory(UpdateHistoryRequestDto updateHistoryRequestDto) {
        historyRepository.findById(updateHistoryRequestDto.getIdHistory())
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.UPDATE_HISTORY_IS_NULL));
        History history = historyMapper.toHistory(updateHistoryRequestDto);
        historyRepository.save(history);
    }

    @Override
    public void deleteHistoryById(UUID idUser) {
        History history = historyRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.DELETE_HISTORY_IS_NULL));
        history.setStatus(TypeEntity.DELETED);
        historyRepository.save(history);
    }


    @Override
    public void deleteAllHistory() {
        List<History> histories = historyRepository.findAll();
        if (historyRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_HISTORY_IS_NULL);
        }
        histories.forEach(history -> history.setStatus(TypeEntity.DELETED));
        historyRepository.saveAll(histories);
    }
}
