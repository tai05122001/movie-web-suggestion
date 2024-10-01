package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.service.dto.request.GetRelatedMovieLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ContentBasedFilteringService {

    Map<Movie, Double> contentBasedFiltering(Movie currentMovie, List<Movie> filteredMovies);
    List<MovieResponseDto> getMovieContentBasedFilteringById(UUID idMovie);

    List<MovieResponseDto> getMovieContentBasedFilteringLogin(GetRelatedMovieLoginRequestDto requestDto);
}
