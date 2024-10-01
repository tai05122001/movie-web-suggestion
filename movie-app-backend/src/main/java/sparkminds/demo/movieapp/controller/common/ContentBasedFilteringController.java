package sparkminds.demo.movieapp.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.ContentBasedFilteringService;
import sparkminds.demo.movieapp.service.dto.request.GetRelatedMovieLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/content-based")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ContentBasedFilteringController {
    private final ContentBasedFilteringService contentBasedFilteringService;

    @GetMapping
    public ResponseEntity<Object> getContentBasedFiltering() {
//        List<Movie> result = contentBasedFilteringService.contentBasedFiltering(M);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{idMovie}")
    public ResponseEntity<List<MovieResponseDto>> getContentBasedFilteringById(@PathVariable UUID idMovie) {
        return ResponseEntity.ok(contentBasedFilteringService.getMovieContentBasedFilteringById(idMovie));
    }

    @PostMapping
    public ResponseEntity<List<MovieResponseDto>> getContentBasedFilteringLogin(@RequestBody GetRelatedMovieLoginRequestDto requestDto) {
        return ResponseEntity.ok(contentBasedFilteringService.getMovieContentBasedFilteringLogin(requestDto));
    }
}
