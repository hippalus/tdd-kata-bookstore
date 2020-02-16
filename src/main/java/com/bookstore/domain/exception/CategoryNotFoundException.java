package com.bookstore.domain.exception;

import com.bookstore.domain.valueobject.CategoryNumber;

public class CategoryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1840898212262981116L;

    public CategoryNotFoundException(CategoryNumber categoryId) {
        super(String.format("Category:%s is not found", categoryId));
    }
}
