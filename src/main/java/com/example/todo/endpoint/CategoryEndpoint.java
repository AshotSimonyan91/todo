package com.example.todo.endpoint;

import com.example.todo.dto.CategoryDto;
import com.example.todo.dto.CreateCategoryRequestDto;
import com.example.todo.entity.Category;
import com.example.todo.mapper.CategoryMapper;
import com.example.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<CategoryDto> register(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        Optional<Category> byName = categoryService.findByName(createCategoryRequestDto.getName());
        if (byName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Category save = categoryService.save(categoryMapper.map(createCategoryRequestDto));
        return ResponseEntity.ok(categoryMapper.mapToDto(save));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryMapper.mapToDto(categoryService.findAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (categoryService.existsById(id)) {
            categoryService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

}
