package com.bookstore.domain.service;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService extends ICheckService{
    @Transactional
    Book saveBook(Book book);
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Book> getAllBooks();
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<Book> getBooksByCategory(CategoryNumber categoryId);
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Book changeBookCategory(BookNumber bookId, CategoryNumber categoryId);
}
