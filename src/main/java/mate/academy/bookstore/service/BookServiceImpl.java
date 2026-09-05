package mate.academy.bookstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.dto.book.UpdateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.BookRepository;
import mate.academy.bookstore.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by id: " + id
                ));

        return bookMapper.toDto(book);
    }

    @Override
    public BookDto save(CreateBookRequestDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book.setCategories(getCategories(bookDto.getCategoryIds()));

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by id: " + id
                ));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setDescription(bookDto.getDescription());
        book.setCoverImage(bookDto.getCoverImage());
        book.setCategories(getCategories(bookDto.getCategoryIds()));

        Book updatedBook = bookRepository.save(book);

        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Can't find book by id: " + id
            );
        }

        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> findAllByCategoryId(
            Long categoryId,
            Pageable pageable
    ) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(
                    "Can't find category by id: " + categoryId
            );
        }

        return bookRepository.findAllByCategoriesId(
                        categoryId,
                        pageable
                )
                .map(bookMapper::toDto);
    }

    private Set<Category> getCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Category> categories = categoryRepository.findAllById(
                categoryIds
        );

        if (categories.size() != categoryIds.size()) {
            Set<Long> foundIds = categories.stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());

            List<Long> missingIds = categoryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new EntityNotFoundException(
                    "Can't find categories by ids: " + missingIds
            );
        }

        return new HashSet<>(categories);
    }
}
