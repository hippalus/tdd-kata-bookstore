package com.bookstore.domain.service;

import com.bookstore.domain.model.Bookstore;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookstoreService {
    @Transactional
    Bookstore saveBookStore(Bookstore bookstore);
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Bookstore> getAllBookstore();
}
