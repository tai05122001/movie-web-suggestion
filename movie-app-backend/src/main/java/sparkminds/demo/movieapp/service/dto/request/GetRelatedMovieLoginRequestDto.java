package sparkminds.demo.movieapp.service.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetRelatedMovieLoginRequestDto {
    private UUID idMovie;
    private String username;
}
