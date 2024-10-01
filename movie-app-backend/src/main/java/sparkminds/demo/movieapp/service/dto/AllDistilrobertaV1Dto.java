package sparkminds.demo.movieapp.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AllDistilrobertaV1Dto {
    private String sourceSentence;
    private List<String> sentences;
}
