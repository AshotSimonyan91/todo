package com.example.todo.service;

import com.example.todo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    void remove(int id);

    User save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);
}
