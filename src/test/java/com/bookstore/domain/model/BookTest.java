package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    public static final String TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE = "Test Driven Development: By Example";
    public static final String PROGRAMMING = "Programming";

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

    @Test//fixme:
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
        Set<Book> bookItems=new HashSet<>();
        bookItems.add(book);

        //create bookstore
        Bookstore bookStore = Bookstore.builder()
                .id(BookStoreNumber.next())
                .bookItems(bookItems)
                .build();

        Bookstore bookstore2 = Bookstore.builder()
                .id(BookStoreNumber.next())
                .bookItems(bookItems)
                .build();

        //when:
        book.toBookStore(bookStore);
        book.toBookStore(bookstore2);

        //then:
        assertThat(book.getBookByBookstore().size()).isEqualTo(1);
    }
    @Test
    void book_price_should_changes_according_to_bookstore_city(){
        //given:
        Book book = Book.builder()
                .name(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE))
                .category(BookCategory.builder()
                        .name(CategoryName.of(PROGRAMMING))
                        .build())
                .price(Money.of(35.98))
                .build();

        //create bookstore
        Bookstore bookstore1 = Bookstore.builder()
                .id(BookStoreNumber.next())
                .city(City.builder()
                        .id(CityNumber.of("Van/Ercis"))
                        .cityName(CityName.of("Van"))
                        .build())
                .build();

        Bookstore bookstore2 = Bookstore.builder()
                .id(BookStoreNumber.next())
                .city(City.builder()
                        .id(CityNumber.of("Istanbul/Pendik"))
                        .cityName(CityName.of("Istanbul"))
                        .build())
                .build();

        //Add to Bookstore book item
        bookstore1.addBook(book,Money.of(15.78));
        bookstore2.addBook(book,Money.of(28.98));

        //when:
        List<Money> priceListOfBookstore1 = bookstore1.getBookItems()
                .stream()
                .map(Book::getPrice)
                .collect(Collectors.toList());

        List<Money> priceListOfBookstore2 = bookstore2.getBookItems()
                .stream()
                .map(Book::getPrice)
                .collect(Collectors.toList());

        //then:
        assertThat(priceListOfBookstore1).isNotEqualTo(priceListOfBookstore2);



    }
}
