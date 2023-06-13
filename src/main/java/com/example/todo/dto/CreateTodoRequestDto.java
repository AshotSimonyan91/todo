package com.example.todo.dto;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoRequestDto {

    private String title;
    private Status status;
    private Category category;
    private User user;
}
