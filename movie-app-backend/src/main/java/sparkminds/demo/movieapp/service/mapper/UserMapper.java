package sparkminds.demo.movieapp.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.service.dto.request.CreateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.response.UserLoginResponseDto;
import sparkminds.demo.movieapp.service.dto.response.UserResponseDto;

@Component
public class UserMapper {
    public User toUser(UpdateUserRequestDto updateUserRequestDto) {
        User user = User.builder().build();
        BeanUtils.copyProperties(updateUserRequestDto, user);
        return user;
    }

    public User toUser(CreateUserRequestDto createUserRequestDto) {
        User user = User.builder().build();
        BeanUtils.copyProperties(createUserRequestDto, user);
        return user;
    }


    public UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = UserResponseDto.builder().build();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }

     public UserLoginResponseDto toUserLoginResponseDto(User user) {
        UserLoginResponseDto userResponseDto = UserLoginResponseDto.builder().build();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }
}
