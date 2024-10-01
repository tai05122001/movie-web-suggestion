package sparkminds.demo.movieapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.UUID;

@Builder
@Data
@Document(collection = "user")
public class User {
    @Id
    private UUID idUser;
    private String email;
    private String username;
    private String password;
    private TypeEntity status;
}
