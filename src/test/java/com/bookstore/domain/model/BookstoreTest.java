package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BookstoreTest {

    @Test
    void book_should_have_a_city(){
        //create bookstore
        Bookstore bookStore = Bookstore.builder()
                .id(BookStoreNumber.next())
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build();

        Assertions.assertThat(bookStore.getCity()).isNotNull();
    }

}