package sparkminds.demo.movieapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Document(collection = "history")
public class History {
    @Id
    private UUID idHistory;
    @DBRef
    private User user;
    @DBRef
    private Movie movie;
    private LocalDateTime viewAt;
    private int watchDuration;
    private TypeEntity status;
    private int rating;

}
