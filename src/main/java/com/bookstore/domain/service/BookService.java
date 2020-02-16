package com.bookstore.domain.service;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    @Transactional()
    Book saveBook(Book book);
    @Transactional
    List<Book> getAllBooks();
    @Transactional
    List<Book> getBooksByCategory(CategoryNumber categoryId);
    @Transactional
    boolean checkCategoryExistence(CategoryNumber categoryId);
    @Transactional
    void deleteAllBooks();

}
