package com.example.todo.service.impl;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;
import com.example.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        if (!userRepository.existsById(id)) return ResponseEntity.noContent().build();
        userRepository.deleteById(id);
        return ResponseEntity.notFound().build();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return null;
        }
        User user = userMapper.createUserRequestDtoToUser(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserAuthResponseDto getUserAuthResponseDto(UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return null;
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return null;
        }
        return new UserAuthResponseDto(tokenUtil.generateToken(userAuthRequestDto.getEmail()));
    }

    @Override
    public ResponseEntity<UserDto> getUserFromUserDto(UserDto userDto, int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
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
        return ResponseEntity.ok(userMapper.userToUserDto(userRepository.save(user)));
    }
}
