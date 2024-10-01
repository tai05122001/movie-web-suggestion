package sparkminds.demo.movieapp.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.CollaborativeFilteringService;
import sparkminds.demo.movieapp.service.dto.request.GetRecommendedMoviesRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/common/similar")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CollaborativeFilteringController {

    private final CollaborativeFilteringService collaborativeFilteringService;

    @PostMapping
    public ResponseEntity<List<MovieResponseDto>> getSimilarMovies(@RequestBody GetRecommendedMoviesRequestDto dto) {
        List<MovieResponseDto> result = collaborativeFilteringService
                .getRecommendedMovies(dto.getUsername(), dto.getNumUserSimilar(), dto.getIdMovieCurrent());
        return ResponseEntity.ok(result);
    }
}
