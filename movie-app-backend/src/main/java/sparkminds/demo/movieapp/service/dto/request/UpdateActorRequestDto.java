package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.ACTOR_FULL_NAME_IS_NULL;

@Builder
@Data
public class UpdateActorRequestDto {
    private UUID idActor;
    @NotNull(message = ACTOR_FULL_NAME_IS_NULL)
    private String fullName;
    private LocalDate birthDate;
}
