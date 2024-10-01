package sparkminds.demo.movieapp.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.service.dto.request.CreateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.response.GenreResponseDto;

@Component
public class GenreMapper {
    public Genre toMovieType(UpdateGenreRequestDto requestDto) {
        Genre genre = Genre.builder().build();
        BeanUtils.copyProperties(requestDto, genre);
        return genre;
    }

     public Genre toMovieType(CreateGenreRequestDto requestDto) {
        Genre genre = Genre.builder().build();
        BeanUtils.copyProperties(requestDto, genre);
        return genre;
    }

    public GenreResponseDto toMovieTypeResponseDto(Genre genre) {
        GenreResponseDto responseDto = GenreResponseDto.builder().build();
        BeanUtils.copyProperties(genre, responseDto);
        return responseDto;
    }

}
