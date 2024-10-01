package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.UUID;

@Builder
@Data
public class UserResponseDto {
    private UUID idUser;
    private String email;
    private String username;
    private String password;
    private TypeEntity status;
}
