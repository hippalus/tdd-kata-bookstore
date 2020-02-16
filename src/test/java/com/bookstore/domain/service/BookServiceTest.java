package com.bookstore.domain.service;

import com.bookstore.domain.exception.CategoryNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.*;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.bookstore.domain.model.BookTest.PROGRAMMING;
import static com.bookstore.domain.model.BookTest.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Test
    void context_load() {
        assertThat(bookService).isNotNull();
    }

    @Test
    void should_create_book() {
        //given:
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("123456"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();

        //when:
        Book persistedBook = bookService.saveBook(book);

        assertThat(persistedBook).isEqualToComparingFieldByField(book);
    }

    @Test
    void should_get_all_book_list() {
        //given:
        Book book1 = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        Book book2 = Book.builder()
                .name(BookName.of("Domain Driven Design"))
                .id(BookNumber.of("741852"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(45.85))
                .build();
        bookService.saveBook(book1);
        bookService.saveBook(book2);


        //when:
        List<Book> bookList = bookService.getAllBooks();

        //then:
        assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    void should_list_book_according_to_a_category() {
        //given:
        Book book1 = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        Book book2 = Book.builder()
                .name(BookName.of("Head First Design Patterns"))
                .id(BookNumber.of("741852"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("12345"))
                        .name(CategoryName.of("Design"))
                        .build())
                .price(Money.of(45.85))
                .build();
        bookService.saveBook(book1);
        bookService.saveBook(book2);

        //when:
        final List<Book> bookByProgrammingCategory = bookService.getBooksByCategory(CategoryNumber.of("741852"));
        final List<Book> booksByDesignCategory = bookService.getBooksByCategory(CategoryNumber.of("12345"));

        //then:
        bookByProgrammingCategory.forEach(book -> {
                    assertThat(book.getCategory().getId()).isEqualTo(CategoryNumber.of("741852"));
                    assertThat(book.getCategory().getName()).isEqualTo(CategoryName.of(PROGRAMMING));

                }
        );
        booksByDesignCategory.forEach(book -> {
                    assertThat(book.getCategory().getId()).isEqualTo(CategoryNumber.of("12345"));
                    assertThat(book.getCategory().getName()).isEqualTo(CategoryName.of("Design"));

                }
        );

    }

    @Test
    void should_throw_exception_when_a_category_doest_exist() {
        //given:
        Book book1 = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        bookService.saveBook(book1);

        //when throw
        assertThatThrownBy(() -> bookService.getBooksByCategory(CategoryNumber.of("123")))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category:123 is not found");
    }

    @Test
    void should_add_a_book_to_bookstore() {
        //given:
        Bookstore bookStore = Bookstore.builder()
                .id(BookStoreNumber.of("123456"))
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build();

        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        Book  persistedBook=bookService.saveBook(book);
        Bookstore persistedBookstore=bookstoreRepository.save(bookStore);

        //when
        persistedBook.toBookStore(persistedBookstore);
        Book result = bookService.saveBook(persistedBook);

        //then:
        result.getBookByBookstore()
                .stream()
                .forEach(bookRegistration -> {
                    assertThat(bookRegistration.getBook()).isEqualToIgnoringGivenFields(persistedBook,"bookByBookstore");
                    assertThat(bookRegistration.getBookStore()).isEqualTo(persistedBookstore);
                });


    }


}
