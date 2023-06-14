package com.example.todo.endpoint;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        User save = userService.save(createUserRequestDto);
        if (save == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(userMapper.userToUserDto(save));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        if (userService.getUserAuthResponseDto(userAuthRequestDto) == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(userService.getUserAuthResponseDto(userAuthRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        return byId.map(user -> ResponseEntity.ok(userMapper.userToUserDto(user))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return userService.remove(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") int id, @RequestBody UserDto userDto) {
        return userService.getUserFromUserDto(userDto, id);
    }
}
