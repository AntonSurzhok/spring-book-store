package mate.academy.bookstore;

import java.math.BigDecimal;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Clean Code");
            book.setAuthor("Robert C. Martin");
            book.setIsbn("9780132350884");
            book.setPrice(new BigDecimal("39.99"));
            book.setDescription("A Handbook of Agile Software Craftsmanship");
            book.setCoverImage("cover.jpg");

            bookService.save(book);

            System.out.println("Books in database:");
            bookService.findAll().forEach(System.out::println);
        };
    }
}
