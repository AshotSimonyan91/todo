package com.example.todo.service;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    Optional<Todo> findById(int id);
    List<Todo> findAll();

    List<Todo> findByUserId(int id);
    List<Todo> findByStatus(Status status);
    List<Todo> findByCategory(Category category);

    void remove(int id);

    Todo save(Todo entity);

    boolean existsById(int id);
}
