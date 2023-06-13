package com.example.todo.repository;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {

    List<Todo> findByUserId(Integer id);
    List<Todo> findByStatus(Status status);
    List<Todo> findByCategory(Category category);
}
