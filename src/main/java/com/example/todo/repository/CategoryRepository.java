package com.example.todo.repository;

import com.example.todo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByName(String name);
}
