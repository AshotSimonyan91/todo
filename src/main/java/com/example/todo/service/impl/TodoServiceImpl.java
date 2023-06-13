package com.example.todo.service.impl;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
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
    public void remove(int id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Todo save(Todo entity) {
        return todoRepository.save(entity);
    }

    @Override
    public boolean existsById(int id) {
        return todoRepository.existsById(id);
    }
}
