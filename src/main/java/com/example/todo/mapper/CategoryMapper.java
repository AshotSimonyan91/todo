package com.example.todo.mapper;

import com.example.todo.dto.CategoryDto;
import com.example.todo.dto.CreateCategoryRequestDto;
import com.example.todo.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category map(CreateCategoryRequestDto createCategoryRequestDto);
    CategoryDto mapToDto(Category category);
    List<CategoryDto> mapToDto(List<Category> categories);

}
