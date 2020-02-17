package com.bookstore.application;

import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.mapper.BookDTOMapper;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.service.BookService;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.CategoryNumber;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookResource {
    private final BookService bookService;
    private final BookDTOMapper bookDTOMapper;

    @RequestMapping(value = "/book/savebook/", method = RequestMethod.POST)
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        Book request = bookDTOMapper.toEntity(bookDTO);
        return ResponseEntity.ok(bookDTOMapper.toDTO(bookService.saveBook(request)));

    }

    @RequestMapping(value = "/book/getallbooks/", method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        final List<BookDTO> books = bookService.getAllBooks()
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = "/book/getbooksbycategory/", method = RequestMethod.POST)
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@RequestParam("categoryId") String categoryId) {
        final List<BookDTO> booksByCategory = bookService.getBooksByCategory(CategoryNumber.of(categoryId))
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(booksByCategory);
    }
    @RequestMapping(value = "/book/changebookcategory/", method = RequestMethod.POST)
    public ResponseEntity<BookDTO> changeBookCategory(@RequestParam("bookId")String bookId,@RequestParam("categoryId") String categoryId) {
        Book book = bookService.changeBookCategory(BookNumber.of(bookId), CategoryNumber.of(categoryId));
        return ResponseEntity.ok(bookDTOMapper.toDTO(book));
    }

    @RequestMapping(value = "/book/addbooktobookstore/", method = RequestMethod.POST)
    public ResponseEntity<BookDTO> addBookToBookstore(@RequestParam("bookId")String bookId,@RequestParam("bookstoreId") String bookstoreId) {
        Book book = bookService.addBookToBookstore(BookNumber.of(bookId), BookStoreNumber.of(bookstoreId));
        return ResponseEntity.ok(bookDTOMapper.toDTO(book));
    }


}
