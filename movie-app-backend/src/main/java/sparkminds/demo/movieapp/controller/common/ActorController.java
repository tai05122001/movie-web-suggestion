package sparkminds.demo.movieapp.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;
import sparkminds.demo.movieapp.service.ActorService;
import sparkminds.demo.movieapp.service.dto.request.CreateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.response.ActorResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/actor")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping("/{idActor}")
    public ResponseEntity<ActorResponseDto> getActorById(@PathVariable UUID idActor) {
        ActorResponseDto responseDto = actorService.getActorById(idActor);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ActorResponseDto>> getAllActors() {
        List<ActorResponseDto> responseDtoList = actorService.getActors();
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<Void> addActor(@Valid @RequestBody CreateActorRequestDto requestDto) {
        actorService.addActor(requestDto);
        return ResponseEntity.status(Status.CREATED.getStatusCode()).body(null);
    }

    @PutMapping
    public ResponseEntity<Void> updateActor(@Valid @RequestBody UpdateActorRequestDto updateActorRequestDto) {
        actorService.updateActor(updateActorRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idActor}")
    public ResponseEntity<Void> deleteActorById(@Valid @PathVariable UUID idActor) {
        actorService.deleteActorById(idActor);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/all")
    public ResponseEntity<Void> deleteAllActors() {
        actorService.deleteAllActors();
        return ResponseEntity.noContent().build();
    }

}
