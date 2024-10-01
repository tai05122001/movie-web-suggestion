package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.UUID;

@Builder
@Data
public class GenreResponseDto {
    private UUID idType;
    private String name;
    private TypeEntity status;
}
