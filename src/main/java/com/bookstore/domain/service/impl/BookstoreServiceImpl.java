package com.bookstore.domain.service.impl;

import com.bookstore.domain.exception.BookNotFoundException;
import com.bookstore.domain.exception.BookStoreNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookstoreService;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.infrastructure.repository.BookRepository;
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
    private final BookRepository bookRepository;

    @Override
    public Bookstore saveBookStore(Bookstore bookstore) {
        return bookstoreRepository.saveAndFlush(bookstore);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Bookstore> getAllBookstore() {
        return bookstoreRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Bookstore removeBook(BookStoreNumber bookstoreId, BookNumber bookId) {
        Bookstore bookStore = getBookStore(bookstoreId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        bookStore.removeBook(book);
        return saveBookStore(bookStore);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Bookstore addBook(BookStoreNumber bookstoreId, BookNumber bookId) {
        Bookstore bookStore = getBookStore(bookstoreId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        bookStore.addBook(book);
        return saveBookStore(bookStore);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Bookstore getBookStore(BookStoreNumber bookstoreId) {
        return bookstoreRepository.findById(bookstoreId)
                .orElseThrow(() -> new BookStoreNotFoundException(bookstoreId));
    }
}
