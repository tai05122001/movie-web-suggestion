package sparkminds.demo.movieapp.service.dto.request;

import lombok.Data;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;

import java.util.UUID;

@Data
public class GetRecommendedMoviesRequestDto {
    private String username;
    private Integer numUserSimilar;
    private UUID idMovieCurrent;
}
