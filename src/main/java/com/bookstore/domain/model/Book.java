package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookName;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.Money;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book implements Serializable {
    private static final long serialVersionUID = 2330454441648787594L;

    private BookName name;
    private BookCategory category;
    private Money price;
    @Builder.Default
    private Set<BookAndBookStore> bookstoresInWhichTheBookIsFound = new HashSet<>();

    public void toBookStore(BookStoreNumber bookStoreNumber) {
        bookstoresInWhichTheBookIsFound.add(BookAndBookStore.bookTo(this, bookStoreNumber));
    }

    @EqualsAndHashCode(exclude = "book")
    @Getter
    public static class BookAndBookStore implements Serializable {
        private static final long serialVersionUID = -7146572463352988820L;

        private Book book;
        private BookStoreNumber bookStoreNumber;

        protected BookAndBookStore() {
        }

        private BookAndBookStore(Book book, BookStoreNumber bookStoreNumber) {
            this.book = book;
            this.bookStoreNumber = bookStoreNumber;
        }

        private static BookAndBookStore bookTo(Book book, BookStoreNumber bookStoreNumber) {
            return new BookAndBookStore(book, bookStoreNumber);

        }
    }
}
