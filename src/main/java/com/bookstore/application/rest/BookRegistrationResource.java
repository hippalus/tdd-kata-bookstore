package com.bookstore.application.rest;

import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.mapper.BookDTOMapper;
import com.bookstore.domain.service.BookRegistrationService;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRegistrationResource {
    private final BookRegistrationService bookRegistrationService;
    private final BookDTOMapper bookDTOMapper;

    @GetMapping(value = "/bookregistration/getbooksbybookstore/")
    public ResponseEntity<List<BookDTO>> getBooksByBookstore(@RequestParam("bookstoreId") String bookstoreId) {
        final List<BookDTO> listOfBooksByBookstore = bookRegistrationService.getBooksByBookstore(BookStoreNumber.of(bookstoreId))
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listOfBooksByBookstore);
    }

    @GetMapping(value = "/bookregistration/getbooksbycategoryandbookstore/")
    public ResponseEntity<List<BookDTO>> getBooksByCategoryAndBookstore(@RequestParam("bookstoreId") String bookstoreId,
                                                                        @RequestParam("categoryId") String categoryId) {
        final List<BookDTO> listOfBooksByCategoryAndBookstore = bookRegistrationService
                .getBooksByCategoryAndBookstore(BookStoreNumber.of(bookstoreId), CategoryNumber.of(categoryId))
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listOfBooksByCategoryAndBookstore);
    }

}


