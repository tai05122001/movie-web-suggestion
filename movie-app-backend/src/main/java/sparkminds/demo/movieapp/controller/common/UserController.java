package sparkminds.demo.movieapp.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.UserService;
import sparkminds.demo.movieapp.service.dto.request.CreateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateUserRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UserLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.UserLoginResponseDto;
import sparkminds.demo.movieapp.service.dto.response.UserResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{isUser}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID isUser) {
        return ResponseEntity.ok(userService.getUserById(isUser));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        userService.addUser(createUserRequestDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(userService.handleLogin(userLoginRequestDto));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        userService.updateUser(updateUserRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isUser}")
    public ResponseEntity<UserResponseDto> deleteUserById(@PathVariable UUID isUser) {
        userService.deleteUserById(isUser);
        return ResponseEntity.noContent().build();
    }

}
