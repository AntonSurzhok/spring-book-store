package mate.academy.bookstore.mapper;

import java.util.stream.Collectors;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto bookDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(
            @MappingTarget BookDto bookDto,
            Book book
    ) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(
                    book.getCategories()
                            .stream()
                            .map(Category::getId)
                            .collect(Collectors.toList())
            );
        }
    }
}
