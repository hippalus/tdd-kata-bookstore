package com.bookstore.domain.service.impl;

import com.bookstore.domain.exception.BookNotFoundException;
import com.bookstore.domain.exception.BookStoreNotFoundException;
import com.bookstore.domain.exception.CategoryNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookService;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import com.bookstore.infrastructure.repository.BookRepository;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository categoryRepository;
    private final BookstoreRepository bookstoreRepository;


    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Book> getBooksByCategory(CategoryNumber categoryId) {
        boolean isCategoryExist = checkCategoryExistence(categoryId);
        if (isCategoryExist) {
            return bookRepository.findByCategory_Id(categoryId);
        } else {
            throw new CategoryNotFoundException(categoryId);
        }
    }
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean checkCategoryExistence(CategoryNumber categoryId) {
        return categoryRepository.existsById(categoryId);
    }
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Book changeBookCategory(BookNumber bookId, CategoryNumber categoryId) {

        BookCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Book book = getBookById(bookId);

        book.setCategory(category);
        return saveBook(book);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Book addBookToBookstore(BookNumber bookId, BookStoreNumber bookstoreId) {
        Book book = getBookById(bookId);
        Bookstore bookstore = getBookStore(bookstoreId);
        book.toBookStore(bookstore);
        return saveBook(book);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Book getBookById(BookNumber bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Bookstore getBookStore(BookStoreNumber bookstoreId) {
        return bookstoreRepository.findById(bookstoreId)
                .orElseThrow(() -> new BookStoreNotFoundException(bookstoreId));
    }

}
