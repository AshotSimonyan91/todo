package com.example.todo.service.impl;

import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void remove(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
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
}
