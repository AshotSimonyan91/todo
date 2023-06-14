package com.example.todo.service;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    ResponseEntity<?> remove(int id);

    User save(User user);
    User save(CreateUserRequestDto createUserRequestDto);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

    UserAuthResponseDto getUserAuthResponseDto(UserAuthRequestDto userAuthRequestDto);

    ResponseEntity<UserDto> getUserFromUserDto(UserDto userDto, int id);
}
