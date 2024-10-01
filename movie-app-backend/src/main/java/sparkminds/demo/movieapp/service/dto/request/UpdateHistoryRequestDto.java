package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.MOVIE_IS_NULL;
import static sparkminds.demo.movieapp.constant.MessageContant.USER_IS_NULL;

@Data
@Builder
public class UpdateHistoryRequestDto {
    private UUID idHistory;
    @NotNull(message = USER_IS_NULL)
    private User user;
    @NotNull(message = MOVIE_IS_NULL)
    private Movie movie;
    private LocalDateTime viewAt;
    private int watchDuration;
    private int rating;
}
