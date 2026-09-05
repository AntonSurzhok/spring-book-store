package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.dto.book.UpdateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto save(CreateBookRequestDto bookDto);

    BookDto update(Long id, UpdateBookRequestDto bookDto);

    void deleteById(Long id);

    Page<BookDto> findAllByCategoryId(
            Long categoryId,
            Pageable pageable
    );
}
