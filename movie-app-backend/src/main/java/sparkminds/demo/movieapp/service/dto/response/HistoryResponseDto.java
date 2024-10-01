package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class HistoryResponseDto {
    private UUID idHistory;
    private User user;
    private Movie movie;
    private LocalDateTime viewAt;
    private int watchDuration;
    private TypeEntity status;
    private int rating;
}
