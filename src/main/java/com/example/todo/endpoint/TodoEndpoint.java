package com.example.todo.endpoint;

import com.example.todo.dto.CreateTodoRequestDto;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.CategoryService;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoEndpoint {

    private final TodoService todoService;
    private final TodoMapper todoMapper;
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<TodoDTO> register(@RequestBody CreateTodoRequestDto createTodoRequestDto,
                                            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(todoMapper.todoToTodoDto(todoService.save(createTodoRequestDto, currentUser)));
    }

    @GetMapping()
    public ResponseEntity<List<TodoDTO>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(todoMapper.todoListToTodoDtoList(todoService.findByUserId(currentUser.getUser().getId())));
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoDTO>> getAllByStatus(@RequestParam("status") String status) {
        return ResponseEntity.ok(todoMapper.todoListToTodoDtoList(todoService.findByStatus(Status.valueOf(status))));
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<TodoDTO>> getAllByCategory(@RequestParam("category") String category) {
        Optional<Category> byName = categoryService.findByName(category);
        return byName.map(value -> ResponseEntity.ok(todoMapper.todoListToTodoDtoList(todoService.findByCategory(value))))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> update(@PathVariable("id") int id, @RequestBody TodoDTO todoDTO) {
        if (todoService.save(todoDTO, id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(todoMapper.todoToTodoDto(todoService.save(todoDTO, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @AuthenticationPrincipal CurrentUser currentUser) {
        return todoService.remove(id, currentUser);
    }
}
