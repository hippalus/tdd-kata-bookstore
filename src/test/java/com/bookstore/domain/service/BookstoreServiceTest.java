package com.bookstore.domain.service;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.bookstore.domain.model.BookTest.PROGRAMMING;
import static com.bookstore.domain.model.BookTest.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookstoreServiceTest {
    @Autowired
    private BookstoreService bookstoreService;

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

}
