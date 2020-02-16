package com.bookstore.infrastructure.repository;

import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.valueobject.BookStoreNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, BookStoreNumber> {
}
