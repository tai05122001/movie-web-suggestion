package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.service.dto.request.CreateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.response.GenreResponseDto;

import java.util.List;
import java.util.UUID;

public interface GenreService {

    List<GenreResponseDto> getAllGenres();

    GenreResponseDto getGenreById(UUID id);

    void addGenre(CreateGenreRequestDto requestDto);

    void updateGenre(UpdateGenreRequestDto requestDto);

    void deleteGenreById(UUID id);

    void deleteAllGenres();
}
