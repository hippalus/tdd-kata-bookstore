package com.bookstore.domain.service.impl;

import com.bookstore.domain.exception.CategoryNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.service.BookService;
import com.bookstore.domain.valueobject.CategoryNumber;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import com.bookstore.infrastructure.repository.BookRepository;
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
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}
