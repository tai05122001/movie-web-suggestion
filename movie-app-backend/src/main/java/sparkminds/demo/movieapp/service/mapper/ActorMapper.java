package sparkminds.demo.movieapp.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sparkminds.demo.movieapp.entity.Actor;
import sparkminds.demo.movieapp.service.dto.request.CreateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateActorRequestDto;
import sparkminds.demo.movieapp.service.dto.response.ActorResponseDto;

@Component
public class ActorMapper {
    public Actor toActor(UpdateActorRequestDto updateActorRequestDto) {
        Actor actor = Actor.builder().build();
        BeanUtils.copyProperties(updateActorRequestDto, actor);
        return actor;
    }

    public Actor toActor(CreateActorRequestDto createActorRequestDto) {
        Actor actor = Actor.builder().build();
        BeanUtils.copyProperties(createActorRequestDto, actor);
        return actor;
    }

    public ActorResponseDto toResponseDto(Actor actor) {
        ActorResponseDto actorResponseDto = ActorResponseDto.builder().build();
        BeanUtils.copyProperties(actor, actorResponseDto);
        return actorResponseDto;
    }
}
