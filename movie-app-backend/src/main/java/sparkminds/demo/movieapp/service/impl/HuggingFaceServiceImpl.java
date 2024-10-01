package sparkminds.demo.movieapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sparkminds.demo.movieapp.service.HuggingFaceService;
import sparkminds.demo.movieapp.service.dto.AllDistilrobertaV1Dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sparkminds.demo.movieapp.constant.UrlConstant.API_KEY;

@Service
@RequiredArgsConstructor
public class HuggingFaceServiceImpl implements HuggingFaceService {
    private final WebClient webClient;

    private final WebClient webClientLocal;

    @Override
    @SneakyThrows
    public String calculateSimilarStrings(String sourceSentence, List<String> sentences) {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        Map<String, AllDistilrobertaV1Dto> inputsMap = new HashMap<>();
        inputsMap.put("inputs", AllDistilrobertaV1Dto.builder().sentences(sentences).sourceSentence(sourceSentence).build());
        // Gửi request và nhận response
        return this.webClient.post()
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(objectMapper
                        .writeValueAsString(inputsMap)
                        .replace("sourceSentence", "source_sentence"))
                .retrieve().bodyToMono(String.class).block();

    }

    @Override
    @SneakyThrows
    public String calculateSimilarStringsLocal(String sourceSentence, List<String> sentences) {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        Map<String, AllDistilrobertaV1Dto> inputsMap = new HashMap<>();
        inputsMap.put("inputs", AllDistilrobertaV1Dto.builder().sentences(sentences).sourceSentence(sourceSentence).build());
        // Gửi request và nhận response
        return this.webClientLocal.post()
                .header("Content-Type", "application/json")
                .bodyValue(objectMapper
                        .writeValueAsString(inputsMap)
                        .replace("sourceSentence", "source_sentence"))
                .retrieve().bodyToMono(String.class).block();

    }


}
