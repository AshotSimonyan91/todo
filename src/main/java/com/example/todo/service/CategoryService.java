package com.example.todo.service;

import com.example.todo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    void remove(int id);

    Category save(Category category);

    Optional<Category> findByName(String name);

    boolean existsById(int id);
}
