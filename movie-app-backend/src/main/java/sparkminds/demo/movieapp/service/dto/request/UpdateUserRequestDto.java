package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.*;

@Builder
@Data
public class UpdateUserRequestDto {
    private UUID idUser;
    @NotNull(message = EMAIL_IS_NULL)
    @Email(message = EMAIL_IS_INVAILD)
    private String email;
    @NotNull(message = USERNAME_IS_NULL)
    private String username;
    @NotNull(message = PASSWORD_IS_NULL)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = PASSWORD_IS_INVALID
    )
    private String password;
}
