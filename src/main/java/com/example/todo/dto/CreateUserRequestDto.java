package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {

    private String name;
    private String surname;
    private String email;
    private String password;
}
