package com.example.todo.endpoint;

import com.example.todo.dto.CreateTodoRequestDto;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
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
        Todo build = Todo.builder()
                .title(createTodoRequestDto.getTitle())
                .category(createTodoRequestDto.getCategory())
                .status(Status.NOT_STARTED)
                .user(currentUser.getUser())
                .build();
        return ResponseEntity.ok(todoMapper.todoToTodoDto(todoService.save(build)));
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
        if (byName.isPresent()) {
            return ResponseEntity.ok(todoMapper.todoListToTodoDtoList(todoService.findByCategory(byName.get())));
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> update(@PathVariable("id") int id, @RequestBody TodoDTO todoDTO) {
        Optional<Todo> byId = todoService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
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
        return ResponseEntity.ok(todoMapper.todoToTodoDto(todoService.save(todo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Todo> byId = todoService.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getUser().getId() == currentUser.getUser().getId()) {
                if (todoService.existsById(id)) {
                    todoService.remove(id);
                    return ResponseEntity.noContent().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
