package sparkminds.demo.movieapp.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.GenreService;
import sparkminds.demo.movieapp.service.dto.request.CreateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateGenreRequestDto;
import sparkminds.demo.movieapp.service.dto.response.GenreResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{idType}")
    public ResponseEntity<GenreResponseDto> getGenreById(@PathVariable UUID idType) {
        return ResponseEntity.ok(genreService.getGenreById(idType));
    }

    @PostMapping
    public ResponseEntity<Void> addMovieType(
            @Valid @RequestBody CreateGenreRequestDto createGenreRequestDto) {
        genreService.addGenre(createGenreRequestDto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMovieType(@Valid @RequestBody UpdateGenreRequestDto updateGenreRequestDto) {
        genreService.updateGenre(updateGenreRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idType}")
    public ResponseEntity<Void> deleteMovieTypeById(@PathVariable UUID idType) {
        genreService.deleteGenreById(idType);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/all")
    public ResponseEntity<Void> deleteAllMovieTyres() {
        genreService.deleteAllGenres();
        return ResponseEntity.noContent().build();
    }
}
