package com.bookstore.infrastructure.repository;

import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository  extends JpaRepository<BookCategory, CategoryNumber> {
}
