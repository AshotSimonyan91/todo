package com.example.todo.service.impl;

import com.example.todo.dto.CreateTodoRequestDto;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;


    @Override
    public Optional<Todo> findById(int id) {
        return todoRepository.findById(id);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> findByUserId(int id) {
        return todoRepository.findByUserId(id);
    }

    @Override
    public List<Todo> findByStatus(Status status) {
        return todoRepository.findByStatus(status);
    }

    @Override
    public List<Todo> findByCategory(Category category) {
        return todoRepository.findByCategory(category);
    }

    @Override
    public ResponseEntity<?> remove(int id, CurrentUser currentUser) {
        Optional<Todo> byId = todoRepository.findById(id);
        if (byId.isEmpty() || byId.get().getUser().getId() != currentUser.getUser().getId() || !todoRepository.existsById(id))
            return ResponseEntity.noContent().build();
        todoRepository.deleteById(id);
        return ResponseEntity.notFound().build();
    }

    @Override
    public Todo save(Todo entity) {
        return todoRepository.save(entity);
    }

    @Override
    public Todo save(TodoDTO todoDTO, int id) {
        Optional<Todo> byId = todoRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Todo todo = byId.get();
        if (todoDTO.getTitle() != null && !todoDTO.getTitle().isEmpty()) {
            todo.setTitle(todoDTO.getTitle());
        }
        if (todoDTO.getStatus() != null) {
            todo.setStatus(todoDTO.getStatus());
        }
        if (todoDTO.getCategory() != null) {
            todo.setCategory(todoDTO.getCategory());
        }
        return todo;
    }

    @Override
    public Todo save(CreateTodoRequestDto createTodoRequestDto, CurrentUser currentUser) {
        if (currentUser != null) {
            return Todo.builder()
                    .title(createTodoRequestDto.getTitle())
                    .category(createTodoRequestDto.getCategory())
                    .status(Status.NOT_STARTED)
                    .user(currentUser.getUser())
                    .build();
        }
        return null;
    }

    @Override
    public boolean existsById(int id) {
        return todoRepository.existsById(id);
    }
}
