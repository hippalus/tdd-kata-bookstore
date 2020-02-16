package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookRegistrationTest {
    @Test
    void  book_registration_equals_hash(){
        BookRegistration bookRegistration=new BookRegistration(Bookstore.builder()
                .id(BookStoreNumber.of("456"))
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build(),Book.builder()
                .name(BookName.of("465"))
                .category(BookCategory.builder()
                        .name(CategoryName.of("65"))
                        .build())
                .price(Money.of(35.98))
                .build());
        BookRegistration bookRegistration2=new BookRegistration(Bookstore.builder()
                .id(BookStoreNumber.of("456"))
                .city(City.builder()
                        .id(CityNumber.of("34"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build(),Book.builder()
                .name(BookName.of("465"))
                .category(BookCategory.builder()
                        .name(CategoryName.of("65"))
                        .build())
                .price(Money.of(35.98))
                .build());
        Set<BookRegistration> books= new HashSet<>();
        books.add(bookRegistration);
        books.add(bookRegistration2);

        assertEquals(1, books.size());
        boolean tr =bookRegistration.equals(bookRegistration2);
        assertTrue(tr);
        boolean hs =bookRegistration.hashCode() == bookRegistration2.hashCode();
        assertTrue(hs);

    }

}