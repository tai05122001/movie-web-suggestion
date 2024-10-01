package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;
import java.util.UUID;

public interface CollaborativeFilteringService {
    List<MovieResponseDto> getRecommendedMovies(String username, Integer numUserSimilar, UUID idMovie);
}
