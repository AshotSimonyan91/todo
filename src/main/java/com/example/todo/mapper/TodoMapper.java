package com.example.todo.mapper;

import com.example.todo.dto.CreateTodoRequestDto;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo createTodoRequestDtoToTodo(CreateTodoRequestDto dto);
    TodoDTO todoToTodoDto(Todo todo);

    List<TodoDTO> todoListToTodoDtoList(List<Todo> todos);

}
