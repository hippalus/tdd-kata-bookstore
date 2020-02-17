package com.bookstore.domain.service;

import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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

}
