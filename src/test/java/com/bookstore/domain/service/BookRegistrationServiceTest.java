package com.bookstore.domain.service;

import com.bookstore.domain.exception.BookCouldNotFoundByBookStoreAndCategory;
import com.bookstore.domain.exception.BookStoreNotFoundException;
import com.bookstore.domain.model.*;
import com.bookstore.domain.valueobject.*;
import com.bookstore.infrastructure.repository.BookRepository;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.bookstore.domain.model.BookTest.PROGRAMMING;
import static com.bookstore.domain.model.BookTest.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class BookRegistrationServiceTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private BookRegistrationService bookRegistrationService;


    @Test
    void should_list_book_according_to_a_bookstore() {
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

        Book persistedBook = bookRepository.save(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStore);

        persistedBook.toBookStore(persistedBookstore);
        bookRepository.save(persistedBook);

        //when:
        final List<Book> bookByIstanbulBookstore = bookRegistrationService.getBooksByBookstore(BookStoreNumber.of("123456"));

        //then:
        bookByIstanbulBookstore
                .parallelStream()
                .forEach(book1 -> {
                    book1.getBookByBookstore()
                            .forEach(bookRegistration -> assertThat(bookRegistration.getBookStore().getCity())
                                    .isEqualTo(City.builder()
                                            .id(CityNumber.of("34"))
                                            .cityName(CityName.of("Istanbul"))
                                            .build()));
                });

    }

    @Test
    void should_throws_BookStoreNotFoundException() {
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

        Book persistedBook = bookRepository.save(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStore);

        persistedBook.toBookStore(persistedBookstore);
        bookRepository.save(persistedBook);

        //when throws
        assertThatThrownBy(() -> bookRegistrationService.getBooksByBookstore(BookStoreNumber.of("12")))
                .isInstanceOf(BookStoreNotFoundException.class)
                .hasMessage("Bookstore:12 is not found");

    }

    @Test
    void should_list_book_according_to_bookstore_and_category() {
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

        Book persistedBook = bookRepository.save(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStore);

        persistedBook.toBookStore(persistedBookstore);
        bookRepository.save(persistedBook);

        //when:
        final List<Book> bookByIstanbulBookstore = bookRegistrationService.getBooksByCategoryAndBookstore(BookStoreNumber.of("123456"), CategoryNumber.of("741852"));

        //then:
        bookByIstanbulBookstore
                .parallelStream()
                .forEach(book1 -> {
                    book1.getBookByBookstore()
                            .forEach(bookRegistration -> {
                                assertThat(bookRegistration.getBookStore().getCity())
                                        .isEqualTo(City.builder()
                                                .id(CityNumber.of("34"))
                                                .cityName(CityName.of("Istanbul"))
                                                .build());
                            });

                });

    }

    @Test
    void should_throw_BookCouldNotFoundByBookStoreAndCategory() {
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

        Book persistedBook = bookRepository.save(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStore);

        persistedBook.toBookStore(persistedBookstore);
        bookRepository.save(persistedBook);

        //when throws
        assertThatThrownBy(() -> bookRegistrationService.getBooksByCategoryAndBookstore(BookStoreNumber.of("12"), CategoryNumber.of("741852")))
                .isInstanceOf(BookCouldNotFoundByBookStoreAndCategory.class)
                .hasMessage("Book could not found according to specified category:741852 and bookstore:12");
    }

    @Test//TODO Book and Book store Cascade Type configuration
    void should_remove_book_from_bookstore() {
        //given:
        Bookstore bookStoreObject = Bookstore.builder()
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

        Book persistedBook = bookRepository.save(book);
        Bookstore persistedBookstore = bookstoreRepository.save(bookStoreObject);

        persistedBookstore.addBook(persistedBook);
        final Book save = bookRepository.save(persistedBook);
        Bookstore bookstore = bookstoreRepository.save(persistedBookstore);

        //when:
        bookstore.removeBook(persistedBook);
        final Bookstore updatedBookstore = bookstoreRepository.save(bookstore);

        //then:
        updatedBookstore
                .getBookItems()
                .stream()
                .forEach(b -> {
                    assertThat(b).isNotEqualTo(save);
                });


    }
}
