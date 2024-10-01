package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.constant.MessageContant;
import sparkminds.demo.movieapp.entity.Actor;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.ActorRepository;
import sparkminds.demo.movieapp.service.ActorService;
import sparkminds.demo.movieapp.service.dto.request.CreateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.response.ActorResponseDto;
import sparkminds.demo.movieapp.service.mapper.ActorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public List<ActorResponseDto> getActors() {
        List<Actor> actors = actorRepository.findAll();
        if (actorRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_ACTOR_IS_NULL);
        }
        List<ActorResponseDto> result = new ArrayList<>();
        actors.forEach(actor -> {
            result.add(actorMapper.toResponseDto(actor));
        });
        return result;
    }

    @Override
    public ActorResponseDto getActorById(UUID id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.ACTOR_IS_NULL));
        return actorMapper.toResponseDto(actor);
    }

    @Override
    public void addActor(CreateActorRequestDto requestDto) {
        Actor actor = actorMapper.toActor(requestDto);
        actor.setStatus(TypeEntity.ACTIVE);
        actor.setIdActor(UUID.randomUUID());
        actorRepository.save(actor);
    }

    @Override
    public void updateActor(UpdateActorRequestDto updateActorRequestDto) {
        actorRepository.findById(updateActorRequestDto.getIdActor())
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.UPDATE_ACTOR_IS_NULL));
        Actor actor = actorMapper.toActor(updateActorRequestDto);
        actor.setStatus(TypeEntity.ACTIVE);
        actorRepository.save(actor);
    }

    @Override
    public void deleteActorById(UUID id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.DELETE_ACTOR_IS_NULL));
        actor.setStatus(TypeEntity.DELETED);
        actorRepository.save(actor);
    }

    @Override
    public void deleteAllActors() {
        List<Actor> actors = actorRepository.findAll();
        if (actorRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_ACTOR_IS_NULL);
        }
        actors.forEach(actor -> actor.setStatus(TypeEntity.DELETED));
        actorRepository.saveAll(actors);
    }
}
