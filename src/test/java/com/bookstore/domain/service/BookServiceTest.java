package com.bookstore.domain.service;

import com.bookstore.domain.exception.BookNotFoundException;
import com.bookstore.domain.exception.CategoryNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.*;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookstore.domain.model.BookTest.PROGRAMMING;
import static com.bookstore.domain.model.BookTest.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private BookCategoryRepository categoryRepository;

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
        Book persistedBook = bookService.saveBook(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStore);

        //when
        persistedBook.toBookStore(persistedBookstore);
        Book result = bookService.saveBook(persistedBook);

        //then:
        result.getBookByBookstore()
                .stream()
                .forEach(bookRegistration -> {
                    assertThat(bookRegistration.getBookStore()).isEqualTo(persistedBookstore);
                });
    }

    @Test
    void should_change_books_category() {
        //given:
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        BookCategory bookCategory = BookCategory.builder()
                .id(CategoryNumber.of("123456789"))
                .name(CategoryName.of("Romance"))
                .build();
        bookService.saveBook(book);
        final BookCategory persistBookCategory = categoryRepository.save(bookCategory);

        //when:
        final Book aCategoryChangingBook = bookService.changeBookCategory(BookNumber.of("123456"), CategoryNumber.of("123456789"));

        //then:
        assertThat(aCategoryChangingBook.getCategory()).isEqualTo(persistBookCategory);

    }

    @Test
    void should_throw_BookNotFoundException_when_change_books_category() {
        //given:
        BookCategory bookCategory = BookCategory.builder()
                .id(CategoryNumber.of("123456789"))
                .name(CategoryName.of("Romance"))
                .build();
        categoryRepository.save(bookCategory);

        //when throws
        assertThatThrownBy(() -> bookService.changeBookCategory(BookNumber.of("74"), CategoryNumber.of("123456789")))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book:74 is not found");

    }

    @Test
    void should_throw_CategoryNotFoundException_when_change_books_category() {
        //given:
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .id(BookNumber.of("123456"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("741852"))
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        bookService.saveBook(book);

        //when throws
        assertThatThrownBy(() -> bookService.changeBookCategory(BookNumber.of("123456"), CategoryNumber.of("121")))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category:121 is not found");

    }


}
