package sparkminds.demo.movieapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.UUID;

@Builder
@Data
@Document(collection = "genre")
public class Genre {
    @Id
    private UUID idType;
    private String name;
    private TypeEntity status;
}
