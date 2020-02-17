package com.bookstore.domain.service;

import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.BookStoreNumber;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookstoreService extends ICheckService{
    @Transactional
    Bookstore saveBookStore(Bookstore bookstore);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Bookstore> getAllBookstore();

    @Transactional(isolation = Isolation.READ_COMMITTED)
    Bookstore removeBook(BookStoreNumber bookstoreId, BookNumber bookId);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    Bookstore addBook(BookStoreNumber bookstoreId, BookNumber bookId);
}
