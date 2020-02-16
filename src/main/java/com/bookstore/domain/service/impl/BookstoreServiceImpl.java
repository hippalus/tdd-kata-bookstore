package com.bookstore.domain.service.impl;

import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookstoreService;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {
    private final BookstoreRepository bookstoreRepository;
    @Override
    public Bookstore saveBookStore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }
}
