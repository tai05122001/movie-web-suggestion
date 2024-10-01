package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import static sparkminds.demo.movieapp.constant.MessageContant.*;

@Builder
@Data
public class CreateUserRequestDto {
    @NotNull(message = EMAIL_IS_NULL)
    @Email(message = EMAIL_IS_INVAILD)
    private String email;
    @NotNull(message = USERNAME_IS_NULL)
    private String username;
    @NotNull(message = PASSWORD_IS_NULL)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}$", message = PASSWORD_IS_INVALID)
    private String password;
}
