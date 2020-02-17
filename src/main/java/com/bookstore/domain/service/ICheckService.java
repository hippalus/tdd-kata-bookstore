package com.bookstore.domain.service;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.OperationNotSupportedException;

public interface ICheckService {
    @SneakyThrows
    @Transactional(isolation = Isolation.READ_COMMITTED)
    default boolean checkCategoryExistence(CategoryNumber categoryId) {
        throw new OperationNotSupportedException();
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.READ_COMMITTED)
    default Bookstore getBookStore(BookStoreNumber bookstoreId) {
        throw new OperationNotSupportedException();
    }
}