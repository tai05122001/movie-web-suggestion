package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.constant.MessageContant;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.GenreRepository;
import sparkminds.demo.movieapp.repository.HistoryRepository;
import sparkminds.demo.movieapp.repository.MovieRepository;
import sparkminds.demo.movieapp.service.HuggingFaceService;
import sparkminds.demo.movieapp.service.MovieService;
import sparkminds.demo.movieapp.service.dto.request.CreateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;
import sparkminds.demo.movieapp.service.mapper.MovieMapper;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final HistoryRepository historyRepository;
    private final GenreRepository genreRepository;
    private final HuggingFaceService huggingFaceService;

    @Override
    public List<MovieResponseDto> getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        if (movieRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_MOVIE_IS_NULL);
        }
        List<MovieResponseDto> movieResponseDtoList = new ArrayList<>();
        movieList.forEach(movie -> movieResponseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return movieResponseDtoList;
    }

    @Override
    public MovieResponseDto getMovieById(UUID id) {
        return movieMapper.toMovieResponseDto(movieRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.MOVIE_IS_NULL)));
    }

    @Override
    public void addMovie(CreateMovieRequestDto requestDto) {
        Movie movie = movieMapper.toMovie(requestDto);
        movie.setStatus(TypeEntity.ACTIVE);
        movie.setIdMovie(UUID.randomUUID());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(UpdateMovieRequestDto updateMovieRequestDto) {
        movieRepository.findById(updateMovieRequestDto.getIdMovie()).orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.UPDATE_MOVIE_IS_NULL));
        movieRepository.save(movieMapper.toMovie(updateMovieRequestDto));
    }

    @Override
    public void deleteMovieById(UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.DELETE_MOVIE_IS_NULL));
        movie.setStatus(TypeEntity.DELETED);
        movieRepository.save(movie);
    }

    @Override
    public void deleteAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        if (movieRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_MOVIE_IS_NULL);
        }
        movieList.forEach(movie -> movie.setStatus(TypeEntity.DELETED));
        movieRepository.saveAll(movieList);
    }

    @Override
    public List<MovieResponseDto> getMoviesBrandByViews() {
        List<MovieResponseDto> responseDtoList = new ArrayList<>();
        movieRepository.findAll().stream().sorted((Comparator.comparing(Movie::getViews)).reversed()).limit(2).forEach(movie -> {
            responseDtoList.add(movieMapper.toMovieResponseDto(movie));
        });
        return responseDtoList;
    }

    @Override
    public List<MovieResponseDto> getMovieByGenre(String nameGenres) {
        Genre genre = genreRepository.findByName(nameGenres)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.GENRE_IS_NOT_FOUND));
        List<MovieResponseDto> responseDtoList = new ArrayList<>();

        movieRepository.findByGenreListContaining(genre)
                .stream()
                .sorted((Comparator.comparing(Movie::getViews)).reversed())
                .limit(10).forEach(movie -> responseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return responseDtoList;
    }

    @Override
    public List<MovieResponseDto> searchMovieByContentBased(String keyword) {
        //Get movie is history
        List<Movie> responseDtoList = movieRepository.findAll();
        List<String> descriptionMovieList = responseDtoList.parallelStream().map(Movie::getDescription).limit(50).toList();
        String result = huggingFaceService.calculateSimilarStringsLocal(keyword, descriptionMovieList);
        String[] response = result.replace("[", "").replace("]", "").split(",");
        Map<Movie, Double> mapSimilar = new HashMap<>();
        for (int i = 0; i < response.length; i++) {
            mapSimilar.put(responseDtoList.get(i), Double.parseDouble(response[i]));
        }
        List<MovieResponseDto> movieResponseDtoList = new ArrayList<>();
        mapSimilar
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .limit(4).map(Map.Entry::getKey).toList().parallelStream()
                .forEach(movie -> movieResponseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return movieResponseDtoList;
    }


}
