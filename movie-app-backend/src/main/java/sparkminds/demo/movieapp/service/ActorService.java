package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.service.dto.request.CreateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.response.ActorResponseDto;

import java.util.List;
import java.util.UUID;

public interface ActorService {
    List<ActorResponseDto> getActors();

    ActorResponseDto getActorById(UUID id);

    void addActor(CreateActorRequestDto createActorRequestDto);

    void updateActor(UpdateActorRequestDto updateActorRequestDto);

    void deleteActorById(UUID id);

    void deleteAllActors();
}
