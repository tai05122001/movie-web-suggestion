package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.service.dto.request.CreateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UserLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.UserLoginResponseDto;
import sparkminds.demo.movieapp.service.dto.response.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getUsers();

    UserResponseDto getUserById(UUID id);

    void addUser(CreateUserRequestDto user);

    void updateUser(UpdateUserRequestDto user);

    void deleteUserById(UUID id);

    void deleteAllUsers();

    UserLoginResponseDto handleLogin(UserLoginRequestDto userLoginRequestDto);
}
