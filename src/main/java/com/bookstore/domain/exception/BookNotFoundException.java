package com.bookstore.domain.exception;

import com.bookstore.domain.valueobject.BookNumber;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(BookNumber bookId) {
        super(String.format("Book:%s is not found",bookId));
    }
}
