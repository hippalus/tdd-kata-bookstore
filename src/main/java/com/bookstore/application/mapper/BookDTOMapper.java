package com.bookstore.application.mapper;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.dto.BookRegistrationDTO;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.BookRegistration;
import com.bookstore.domain.valueobject.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookDTOMapper {
    private final BookRegistrationDTOMapper bookRegistrationDTOMapper;

    @PostConstruct
    public void init() {
        bookRegistrationDTOMapper.setBookDTOMapper(this);
    }

    public BookRegistrationDTOMapper getBookRegistrationDTOMapper() {
        return bookRegistrationDTOMapper;
    }

    public Book toEntity(BookDTO bookDTO) {
        BookNumber bookId = Objects.nonNull(bookDTO.getId()) ?
                BookNumber.of(bookDTO.getId()) :
                BookNumber.next();

        CategoryNumber categoryId = Objects.nonNull(bookDTO.getCategory().getId()) ?
                CategoryNumber.of(bookDTO.getCategory().getId()) :
                CategoryNumber.next();

        final Set<BookRegistration> bookByBookstore = bookDTO.getBookByBookstore()
                .stream()
                .map(bookRegistrationDTOMapper::toEntity)
                .collect(Collectors.toSet());

        final Set<Book> bookItems = bookDTO.getCategory().getBookItems()
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());

        return Book.builder()
                .id(bookId)
                .name(BookName.of(bookDTO.getName()))
                .price(Money.of(bookDTO.getPrice()))
                .category(BookCategory.builder()
                        .id(categoryId)
                        .name(CategoryName.of(bookDTO.getCategory().getName()))
                        .bookItems(bookItems)
                        .build())
                .bookByBookstore(bookByBookstore)
                .build();

    }

    public BookDTO toDTO(Book book) {

        final Set<BookRegistrationDTO> bookByBookstore = book.getBookByBookstore()
                .stream()
                .map(bookRegistrationDTOMapper::toDTO)
                .collect(Collectors.toSet());

        final Set<BookDTO> bookItems = book.getCategory().getBookItems()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());

        return BookDTO.builder()
                .id(book.getId().getValue())
                .name(book.getName().getValue())
               // .bookByBookstore(bookByBookstore)
                .price(book.getPrice().getValue())
                .category(BookCategoryDTO.builder()
                        .id(book.getCategory().getId().getValue())
                        .name(book.getCategory().getName().getValue())
                        .bookItems(bookItems)
                        .build())
                .build();
    }
}
