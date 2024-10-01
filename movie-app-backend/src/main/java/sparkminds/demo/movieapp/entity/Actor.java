package sparkminds.demo.movieapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Document(collection = "actor")
public class Actor {
    @Id
    private UUID idActor;

    private String fullName;
    private LocalDate birthDate;
    private TypeEntity status;
}
