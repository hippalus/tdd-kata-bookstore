package com.bookstore.domain.exception;

import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;

public class BookCouldNotFoundByBookStoreAndCategory extends RuntimeException {
    public BookCouldNotFoundByBookStoreAndCategory(BookStoreNumber bookstoreId, CategoryNumber categoryId) {
        super(String.format("Book could not found according to specified category:%s and bookstore:%s", categoryId, bookstoreId));
    }
}
