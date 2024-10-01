package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.constant.MessageContant;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.GenreRepository;
import sparkminds.demo.movieapp.service.GenreService;
import sparkminds.demo.movieapp.service.dto.request.CreateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.response.GenreResponseDto;
import sparkminds.demo.movieapp.service.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreResponseDto> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        if (genreRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_GENRE_IS_NULL);
        }
        List<GenreResponseDto> result = new ArrayList<>();
        genres.forEach(movieType -> {
            result.add(genreMapper.toMovieTypeResponseDto(movieType));
        });
        return result;
    }

    @Override
    public GenreResponseDto getGenreById(UUID id) {
        return genreMapper.toMovieTypeResponseDto(genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.GENRE_IS_NOT_FOUND)));
    }

    @Override
    public void addGenre(CreateGenreRequestDto requestDto) {
        Genre genre = genreMapper.toMovieType(requestDto);
        genre.setStatus(TypeEntity.ACTIVE);
        genre.setIdType(UUID.randomUUID());
        genreRepository.save(genre);
    }

    @Override
    public void updateGenre(UpdateGenreRequestDto updateGenreRequestDto) {
        genreRepository.findById(updateGenreRequestDto.getIdType())
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.UPDATE_GENRE_IS_NULL));
        genreRepository.save(genreMapper.toMovieType(updateGenreRequestDto));
    }

    @Override
    public void deleteGenreById(UUID id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.DELETE_GENRE_IS_NULL));
        genre.setStatus(TypeEntity.DELETED);
        genreRepository.save(genre);
    }

    @Override
    public void deleteAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        if (genreRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.GENRE_IS_NULL);
        }
        genres.forEach(movieType -> movieType.setStatus(TypeEntity.DELETED));
        genreRepository.saveAll(genres);
    }
}
