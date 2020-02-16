package com.bookstore.infrastructure.repository;
import com.bookstore.domain.model.BookRegistration;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRegistrationRepository extends JpaRepository<BookRegistration,Long> {
    List<BookRegistration> findByBookStore_Id(BookStoreNumber storeId);
    List<BookRegistration> findByBookStore_IdAndBook_Category_Id(BookStoreNumber bookStoreId, CategoryNumber categoryId);

}
