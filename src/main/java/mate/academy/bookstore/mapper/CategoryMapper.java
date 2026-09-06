package mate.academy.bookstore.mapper;

import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.dto.category.CreateCategoryDto;
import mate.academy.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryDto categoryDto);

    void updateCategoryFromDto(
            CreateCategoryDto categoryDto,
            @MappingTarget Category category
    );
}
