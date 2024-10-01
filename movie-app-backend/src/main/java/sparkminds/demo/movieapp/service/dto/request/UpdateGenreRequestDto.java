package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.MOVIE_TYPE_IS_NULL;

@Builder
@Data
public class UpdateGenreRequestDto {
    private UUID idType;
    @NotNull(message = MOVIE_TYPE_IS_NULL)
    private String name;
}
