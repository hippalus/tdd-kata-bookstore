package com.bookstore.infrastructure.repository;

import com.bookstore.domain.model.Book;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, BookNumber> {
    List<Book> findByCategory_Id(CategoryNumber categoryId);
}
