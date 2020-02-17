package com.bookstore.domain.service;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRegistrationService extends ICheckService {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Book> getBooksByBookstore(BookStoreNumber bookstoreId);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Book> getBooksByCategoryAndBookstore(BookStoreNumber bookstoreId, CategoryNumber categoryId);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    boolean checkBookstoreExistence(BookStoreNumber bookStoreId);
}

