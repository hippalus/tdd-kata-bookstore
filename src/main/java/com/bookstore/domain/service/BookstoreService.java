package com.bookstore.domain.service;

import com.bookstore.domain.model.Bookstore;
import org.springframework.transaction.annotation.Transactional;

public interface BookstoreService {
    @Transactional
    Bookstore saveBookStore(Bookstore bookstore);
}
