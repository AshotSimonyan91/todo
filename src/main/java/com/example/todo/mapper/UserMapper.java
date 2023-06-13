package com.example.todo.mapper;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.mapstruct.Mapper;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createUserRequestDtoToUser(CreateUserRequestDto dto);
    UserDto userToUserDto(User user);

}
