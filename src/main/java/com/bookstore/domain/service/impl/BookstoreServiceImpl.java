package com.bookstore.domain.service.impl;

import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookstoreService;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {
    private final BookstoreRepository bookstoreRepository;
    @Override
    public Bookstore saveBookStore(Bookstore bookstore) {
        return bookstoreRepository.saveAndFlush(bookstore);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Bookstore> getAllBookstore() {
        return bookstoreRepository.findAll();
    }
}
