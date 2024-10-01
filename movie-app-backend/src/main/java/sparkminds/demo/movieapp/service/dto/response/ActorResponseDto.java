package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class ActorResponseDto {
    private UUID idActor;
    private String fullName;
    private LocalDate birthDate;
    private TypeEntity status;
}
