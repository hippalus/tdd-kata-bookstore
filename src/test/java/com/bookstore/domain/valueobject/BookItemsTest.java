package com.bookstore.domain.valueobject;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class BookItemsTest extends ValueObjectContractTest {

    public BookItemsTest() {
        super(newBookItems(), newBookItems().toString());
    }
    public static Object[] equalsInstances() {
        return new Object[]{newBookItems(),newBookItems()};
    }


    public static Object[] nonEqualsInstances() {
        return new Object[]{differentBookItems(), differentBookItems()};
    }

    @Test
    void book_items_should_have_construct_with_bulk_data() {
        //given:
        Set<Book> bookItems=new HashSet<>();
        bookItems.add(newBook("Domain Driven Design"));
        bookItems.add(newBook("Refactoring"));

        //when
        BookItems items=BookItems.of(bookItems);

        //then:
        assertThat(items.getItems()).isEqualTo(bookItems);
    }
    @Test
    void should_fail_fully_if_add_invalid_item() {

       assertThatIllegalArgumentException()
               .isThrownBy(() -> BookItems.of(null))
               .withMessage("Book items should not be null");
    }
    @Test
    void should_fail_fully_if_add_invalid_book() {
        BookItems items=BookItems.aNew();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> items.add(null))
        .withMessage("Book item should not be null");
    }



    public static BookItems newBookItems() {
        //create new books
        Book book = newBook("Test Driven Development: By Example");
        Book book2 = newBook("Pragmatic Programmer");
        // create book items
        BookItems bookItems = BookItems.aNew();
        bookItems.add(book);
        bookItems.add(book2);
        return bookItems;
    }
    public static BookItems differentBookItems() {
        //create new books
        Book book = newBook("Design Patterns");
        Book book2 = newBook("Data Structures");
        // create book items
        BookItems bookItems = BookItems.aNew();
        bookItems.add(book);
        bookItems.add(book2);
        return bookItems;
    }


    private static Book newBook(String name) {
        return Book.builder()
                .name(BookName.of(name))
                .category(BookCategory.builder()
                        .name(CategoryName.of("Programming"))
                        .build())
                .price(Money.of(35.98))
                .build();
    }
}