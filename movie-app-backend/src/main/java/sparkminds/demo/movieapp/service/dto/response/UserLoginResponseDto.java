package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDto {
    String username;
    String email;
}
