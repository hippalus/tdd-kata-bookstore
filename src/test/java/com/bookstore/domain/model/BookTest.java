package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    private static final String TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE = "Test Driven Development: By Example";
    private static final String PROGRAMMING = "Programming";

    @Test
    void book_should_have_a_name() {
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .build();
        assertThat(book.getName()).isEqualTo(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE));
    }

    @Test
    void book_should_have_a_category() {

        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .category(BookCategory.builder()
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .build();

        assertThat(book.getCategory()).isEqualTo(BookCategory.builder()
                .name(CategoryName.of(PROGRAMMING))
                .build());
        assertThat(book.getCategory().getName()).isEqualTo(CategoryName.of(PROGRAMMING));
    }

    @Test
    void book_should_have_a_price() {

        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .category(BookCategory.builder()
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();

        assertThat(book.getCategory()).isEqualTo(BookCategory.builder()
                .name(CategoryName.of(PROGRAMMING))
                .build());
        assertThat(book.getCategory().getName()).isEqualTo(CategoryName.of(PROGRAMMING));
    }

    @Test
    void book_can_be_exists_in_multiple_bookstore() {
        //given:
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .category(BookCategory.builder()
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();
        // create book items
        BookItems bookItems = BookItems.aNew();
        bookItems.add(book);

        //create bookstore
        BookStore bookStore = BookStore.builder()
                .id(BookStoreNumber.next())
                .bookItems(bookItems)
                .build();

        BookStore bookStore2 = BookStore.builder()
                .id(BookStoreNumber.next())
                .bookItems(bookItems)
                .build();

        //when:
        book.toBookStore(bookStore.getId());
        book.toBookStore(bookStore2.getId());

        //then:
        assertThat(book.getBookstoresInWhichTheBookIsFound().size()).isEqualTo(2);
    }
}
