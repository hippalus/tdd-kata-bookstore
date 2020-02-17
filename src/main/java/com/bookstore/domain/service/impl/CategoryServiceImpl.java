package com.bookstore.domain.service.impl;

import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.service.CategoryService;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final BookCategoryRepository categoryRepository;

    @Override
    public BookCategory saveCategory(BookCategory bookCategory) {
        return categoryRepository.saveAndFlush(bookCategory);
    }

    @Override
    public List<BookCategory> getAllBookCategories() {
        return categoryRepository.findAll();
    }
}
