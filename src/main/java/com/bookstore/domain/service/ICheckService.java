package com.bookstore.domain.service;

import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface ICheckService {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    boolean checkCategoryExistence(CategoryNumber categoryId);
}
