package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
