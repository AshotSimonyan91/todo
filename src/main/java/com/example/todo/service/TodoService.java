package com.example.todo.service;

import com.example.todo.dto.CreateTodoRequestDto;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.security.CurrentUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    Optional<Todo> findById(int id);
    List<Todo> findAll();

    List<Todo> findByUserId(int id);
    List<Todo> findByStatus(Status status);
    List<Todo> findByCategory(Category category);

    ResponseEntity<?> remove(int id,CurrentUser currentUser);

    Todo save(Todo entity);
    Todo save(TodoDTO entity,int id);
    Todo save(CreateTodoRequestDto createTodoRequestDto, CurrentUser currentUser);

    boolean existsById(int id);
}
