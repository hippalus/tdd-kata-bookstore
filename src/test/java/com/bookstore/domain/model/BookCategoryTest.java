package com.bookstore.domain.model;


import com.bookstore.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class BookCategoryTest {

    @Test
    void books_should_belongs_to_a_category() {
        //given
        Set<Book> bookItems = new HashSet<>();
        bookItems.add(Book.builder()
                .name(BookName.of("Book"))
                .category(BookCategory.builder()
                        .id(CategoryNumber.of("123456789"))
                        .name(CategoryName.of("Romance"))
                        .build())
                .price(Money.of(35.98))
                .build());

        //when:
        BookCategory bookCategory = BookCategory.builder()
                .id(CategoryNumber.of("123456789"))
                .name(CategoryName.of("Romance"))
                .bookItems(BookItems.of(bookItems))
                .build();
        //then
        bookCategory.getBookItems()
                .getItems()
                .stream()
                .forEach(book -> assertThat(book.getCategory().getId())
                        .isEqualTo(bookCategory.getId()));

    }


}