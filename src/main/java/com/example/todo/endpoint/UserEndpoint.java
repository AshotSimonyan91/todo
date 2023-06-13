package com.example.todo.endpoint;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.service.UserService;
import com.example.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.createUserRequestDtoToUser(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setRole(Role.USER);
        return ResponseEntity.ok(userMapper.userToUserDto(userService.save(user)));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new UserAuthResponseDto(tokenUtil.generateToken(userAuthRequestDto.getEmail())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(userMapper.userToUserDto(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (userService.existsById(id)) {
            userService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") int id, @RequestBody UserDto userDto) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> byEmail = userService.findByEmail(userDto.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = byId.get();
        if (userDto.getName() != null && !userDto.getName().isEmpty()) {
            user.setName(userDto.getName());
        }
        if (userDto.getSurname() != null && !userDto.getSurname().isEmpty()) {
            user.setSurname(userDto.getSurname());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        return ResponseEntity.ok(userMapper.userToUserDto(userService.save(user)));
    }
}
