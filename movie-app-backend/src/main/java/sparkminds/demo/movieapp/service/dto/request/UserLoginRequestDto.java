package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import static sparkminds.demo.movieapp.constant.MessageContant.*;

@Data
@Builder
public class UserLoginRequestDto {
    @Email(message = EMAIL_IS_INVALID)
    @NotNull(message = EMAIL_IS_NULL)
    String email;
    @NotNull(message = PASSWORD_IS_NULL)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}$", message = PASSWORD_IS_INVALID)
    String password;
}
