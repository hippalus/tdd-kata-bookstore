package com.bookstore.domain.exception;

import com.bookstore.domain.valueobject.BookStoreNumber;

public class BookStoreNotFoundException extends RuntimeException {
    public BookStoreNotFoundException(BookStoreNumber bookstoreId) {
         super(String.format("Bookstore:%s is not found", bookstoreId));
    }
}
