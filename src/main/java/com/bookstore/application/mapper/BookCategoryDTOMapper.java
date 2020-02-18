package com.bookstore.application.mapper;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.valueobject.CategoryName;
import com.bookstore.domain.valueobject.CategoryNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookCategoryDTOMapper {
    private final BookDTOMapper bookDTOMapper;

    public BookCategory toEntity(BookCategoryDTO categoryDTO) {
        CategoryNumber categoryId = Objects.nonNull(categoryDTO.getId()) ?
                CategoryNumber.of(categoryDTO.getId()) :
                CategoryNumber.next();

        final Set<Book> bookItems = categoryDTO.getBookItems()
                .stream()
                .map(bookDTOMapper::toEntity)
                .collect(Collectors.toSet());

        return BookCategory.builder()
                .id(categoryId)
                .name(CategoryName.of(categoryDTO.getName()))
                .bookItems(bookItems)
                .build();
    }

    public BookCategoryDTO toDTO(BookCategory bookCategory) {

        final Set<BookDTO> bookItems = bookCategory.getBookItems()
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toSet());

        return BookCategoryDTO.builder()
                .id(bookCategory.getId().getValue())
                .name(bookCategory.getName().getValue())
                .bookItems(bookItems)
                .build();
    }
}
