package com.example.todo.service.impl;

import com.example.todo.entity.Category;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void remove(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public boolean existsById(int id) {
        return categoryRepository.existsById(id);
    }

}
