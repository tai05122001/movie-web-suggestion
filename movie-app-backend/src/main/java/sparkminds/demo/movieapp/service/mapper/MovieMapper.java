package sparkminds.demo.movieapp.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.service.dto.request.CreateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

@Component
public class MovieMapper {
    public Movie toMovie(UpdateMovieRequestDto updateMovieRequestDto) {
        Movie movie = Movie.builder().build();
        BeanUtils.copyProperties(updateMovieRequestDto, movie);
        return movie;
    }

    public Movie toMovie(CreateMovieRequestDto createMovieRequestDto) {
        Movie movie = Movie.builder().build();
        BeanUtils.copyProperties(createMovieRequestDto, movie);
        return movie;
    }

    public MovieResponseDto toMovieResponseDto(Movie movie) {
        MovieResponseDto movieResponseDto = MovieResponseDto.builder().build();
        BeanUtils.copyProperties(movie, movieResponseDto);
        return movieResponseDto;
    }
}
