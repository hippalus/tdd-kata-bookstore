package com.bookstore.application.rest;

import com.bookstore.application.dto.BookStoreDTO;
import com.bookstore.application.mapper.BookStoreDTOMapper;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookstoreService;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.BookStoreNumber;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookstoreResource {
    private final BookStoreDTOMapper bookStoreDTOMapper;
    private final BookstoreService bookstoreService;


    @PostMapping(value = "/bookstore/savebookstore/")
    public ResponseEntity<BookStoreDTO> saveBookStore(@RequestBody BookStoreDTO bookStoreDTO) {
        Bookstore request = bookStoreDTOMapper.toEntity(bookStoreDTO);
        return ResponseEntity.ok(bookStoreDTOMapper.toDTO(bookstoreService.saveBookStore(request)));
    }

    @GetMapping(value = "/bookstore/getallbookstore/")
    public ResponseEntity<List<BookStoreDTO>> getAllBookstore() {
        final List<BookStoreDTO> allBookStores = bookstoreService.getAllBookstore()
                .stream()
                .map(bookStoreDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allBookStores);
    }

    @PostMapping(value = "/bookstore/removebook/")
    public ResponseEntity<BookStoreDTO> removeBook(@RequestParam("bookstoreId") String bookstoreId,
                                                   @RequestParam("bookId") String bookId) {
        final Bookstore bookstore = bookstoreService.removeBook(BookStoreNumber.of(bookstoreId),
                                                                BookNumber.of(bookId));
        return ResponseEntity.ok(bookStoreDTOMapper.toDTO(bookstore));
    }

}
