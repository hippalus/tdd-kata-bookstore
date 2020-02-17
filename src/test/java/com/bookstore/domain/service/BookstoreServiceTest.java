package com.bookstore.domain.service;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.*;
import com.bookstore.infrastructure.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bookstore.domain.model.BookTest.PROGRAMMING;
import static com.bookstore.domain.model.BookTest.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookstoreServiceTest {
    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void context_load() {
        assertThat(bookstoreService).isNotNull();
    }

    @Test
    void should_create_bookstore() {
        //given:
        Bookstore bookStore = Bookstore.builder()
                .id(BookStoreNumber.of("123456"))
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build();

        //when:
        Bookstore persistedBookstore = bookstoreService.saveBookStore(bookStore);
        //then:
        assertThat(persistedBookstore).isEqualToComparingFieldByField(bookStore);
    }

    @Test
    void should_get_all_bookstore_list() {
        //given:
        Bookstore bookStore1 = Bookstore.builder()
                .id(BookStoreNumber.of("123456"))
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build();
        Bookstore bookStore2 = Bookstore.builder()
                .id(BookStoreNumber.of("1245"))
                .city(City.builder()
                        .id(CityNumber.of("65"))
                        .cityName(CityName.of("Van"))
                        .build())
                .build();
        Bookstore persistedBookstore1 = bookstoreService.saveBookStore(bookStore1);
        Bookstore persistedBookstore2 = bookstoreService.saveBookStore(bookStore2);
        List<Bookstore> expectedBookstores = new ArrayList<>();
        expectedBookstores.add(persistedBookstore1);
        expectedBookstores.add(persistedBookstore2);

        //when:
        final List<Bookstore> allBookstore = bookstoreService.getAllBookstore();

        //then:
        assertThat(allBookstore).isEqualTo(expectedBookstores);
    }
    @Test
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
        Bookstore persistedBookstore = bookstoreService.saveBookStore(bookStoreObject);

        persistedBookstore.addBook(persistedBook);
        final Book save = bookRepository.save(persistedBook);
        Bookstore bookstore = bookstoreService.saveBookStore(persistedBookstore);

        //when:
        final Bookstore updatedBookstore = bookstoreService.removeBook(bookstore.getId(),persistedBook.getId());

        //then:
        updatedBookstore
                .getBookItems()
                .forEach(b -> {
                    assertThat(b).isNotEqualTo(save);
                    assertThat(b).isNull();
                });

    }
    @Test
    void should_add_book_to_bookstore(){
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
        Bookstore persistedBookstore = bookstoreService.saveBookStore(bookStoreObject);

        //when:
        Bookstore bookstore = bookstoreService.addBook(persistedBookstore.getId(),persistedBook.getId());

        //then:
        assertThat(bookstore.getBookItems().size()).isEqualTo(1);

        bookstore.getBookItems().forEach(bookItem -> {
            assertThat(bookItem).isEqualTo(persistedBook);

        });

    }

}
