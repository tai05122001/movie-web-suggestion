package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.constant.MessageContant;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.UserRepository;
import sparkminds.demo.movieapp.service.UserService;
import sparkminds.demo.movieapp.service.dto.request.CreateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UserLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.UserLoginResponseDto;
import sparkminds.demo.movieapp.service.dto.response.UserResponseDto;
import sparkminds.demo.movieapp.service.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.USER_IS_NOT_FOUND;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> userList = userRepository.findAll();
        if (userRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_USER_IS_NULL);
        }
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        userList.forEach(user -> {
            responseDtoList.add(userMapper.toUserResponseDto(user));
        });
        return responseDtoList;
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        return userMapper.toUserResponseDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.MOVIE_IS_NOT_FOUND)));
    }

    @Override
    public void addUser(CreateUserRequestDto requestDto) {
        User user = userMapper.toUser(requestDto);
        user.setIdUser(UUID.randomUUID());
        user.setStatus(TypeEntity.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserRequestDto requestDto) {
        userRepository.findById(requestDto.getIdUser())
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.UPDATE_USER_IS_NULL));
        userRepository.save(userMapper.toUser(requestDto));
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.DELETE_USER_IS_NULL));
        user.setStatus(TypeEntity.DELETED);
        userRepository.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userRepository.findAll().isEmpty()) {
            throw new BadRequestException(MessageContant.CommonMessange.LIST_USER_IS_NULL);
        }
        userList.forEach(user -> user.setStatus(TypeEntity.DELETED));
        userRepository.saveAll(userList);
    }

    @Override
    public UserLoginResponseDto handleLogin(UserLoginRequestDto requestDto) {
        User user  = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(MessageContant.CommonMessange.USER_IS_NOT_FOUND));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new BadRequestException("Password incorrect");
        }
        return userMapper.toUserLoginResponseDto(user);
    }
}
