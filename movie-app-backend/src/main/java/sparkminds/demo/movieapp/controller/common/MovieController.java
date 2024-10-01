package sparkminds.demo.movieapp.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.MovieService;
import sparkminds.demo.movieapp.service.dto.request.CreateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateMovieRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/genre/{nameGenre}")
    public ResponseEntity<List<MovieResponseDto>> getMovieByGenre(@PathVariable String nameGenre) {
        return ResponseEntity.ok(movieService.getMovieByGenre(nameGenre));
    }

    @GetMapping("/{idMovie}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable UUID idMovie) {
        return ResponseEntity.ok(movieService.getMovieById(idMovie));
    }

    @GetMapping("/movie-brand")
    public ResponseEntity<List<MovieResponseDto>> getMoviesBrandByViews() {
        return ResponseEntity.ok(movieService.getMoviesBrandByViews());
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> addMovie(@Valid @RequestBody CreateMovieRequestDto requestDto) {
        movieService.addMovie(requestDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMovie(@Valid @RequestBody UpdateMovieRequestDto updateMovieRequestDto) {
        movieService.updateMovie(updateMovieRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idMovie}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable UUID idMovie) {
        movieService.deleteMovieById(idMovie);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/all")
    public ResponseEntity<Void> deleteAllMovies() {
        movieService.deleteAllMovies();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDto>> searchMovieByContentBased(@RequestParam String keyword) {
        return ResponseEntity.ok(movieService.searchMovieByContentBased(keyword));
    }
}
