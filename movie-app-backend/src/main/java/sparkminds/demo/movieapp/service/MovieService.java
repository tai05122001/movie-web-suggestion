package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.service.dto.request.CreateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<MovieResponseDto> getAllMovies();

    MovieResponseDto getMovieById(UUID id);

    void addMovie(CreateMovieRequestDto createMovieRequestDto);

    void updateMovie(UpdateMovieRequestDto requestDto);

    void deleteMovieById(UUID id);

    void deleteAllMovies();

    List<MovieResponseDto> getMoviesBrandByViews();
    List<MovieResponseDto> getMovieByGenre(String name);

    List<MovieResponseDto> searchMovieByContentBased(String keyword);
}
