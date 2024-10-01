package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.*;

@Data
@Builder
public class CreateHistoryRequestDto {
    @NotNull(message = USER_NAME_IS_NULL)
    private String username;
    @NotNull(message = ID_MOVIE_IS_NULL)
    private UUID idMovie;
    @NotNull(message = RATING_IS_NULL)
    private int rating;
    @NotNull(message = VIEW_AT_IS_NULL)
    private LocalDateTime viewAt;
}
