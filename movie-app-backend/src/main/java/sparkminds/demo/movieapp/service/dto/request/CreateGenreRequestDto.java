package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static sparkminds.demo.movieapp.constant.MessageContant.MOVIE_TYPE_IS_NULL;

@Data
public class CreateGenreRequestDto {
    @NotNull(message = MOVIE_TYPE_IS_NULL)
    private String name;
}
