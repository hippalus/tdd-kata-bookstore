package com.bookstore.domain.service;

import com.bookstore.domain.model.BookCategory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    @Transactional
    BookCategory saveCategory(BookCategory bookCategory);
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<BookCategory> getAllBookCategories();
}
