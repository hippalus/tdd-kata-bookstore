package com.bookstore.domain.service.impl;

import com.bookstore.domain.exception.BookCouldNotFoundByBookStoreAndCategory;
import com.bookstore.domain.exception.BookStoreNotFoundException;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookRegistration;
import com.bookstore.domain.service.BookRegistrationService;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import com.bookstore.infrastructure.repository.BookCategoryRepository;
import com.bookstore.infrastructure.repository.BookRegistrationRepository;
import com.bookstore.infrastructure.repository.BookstoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookRegistrationServiceImpl implements BookRegistrationService {

    private final BookRegistrationRepository registrationRepository;
    private final BookstoreRepository bookstoreRepository;
    private final BookCategoryRepository categoryRepository;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Book> getBooksByBookstore(BookStoreNumber bookstoreId) {
        final boolean bookstoreExistence = checkBookstoreExistence(bookstoreId);
        if(bookstoreExistence){
            List<BookRegistration> byBookStoreId = registrationRepository.findByBookStore_Id(bookstoreId);
            return byBookStoreId.stream()
                    .map(BookRegistration::getBook)
                    .collect(Collectors.toList());
        }else {
            throw new BookStoreNotFoundException(bookstoreId);
        }

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Book> getBooksByCategoryAndBookstore(BookStoreNumber bookstoreId, CategoryNumber categoryId) {
        final boolean bookstoreExistence = checkBookstoreExistence(bookstoreId);
        final boolean categoryExistence = checkCategoryExistence(categoryId);
        if (bookstoreExistence && categoryExistence){
            final List<BookRegistration> bookRegistrationList = registrationRepository
                    .findByBookStore_IdAndBook_Category_Id(bookstoreId, categoryId);

            return bookRegistrationList
                    .stream()
                    .map(BookRegistration::getBook)
                    .collect(Collectors.toList());
        }else{
            throw new BookCouldNotFoundByBookStoreAndCategory(bookstoreId,categoryId);
        }

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean checkBookstoreExistence(BookStoreNumber bookStoreId) {
        return bookstoreRepository.existsById(bookStoreId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean checkCategoryExistence(CategoryNumber categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
