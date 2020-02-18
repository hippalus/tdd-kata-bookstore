package com.bookstore.application.rest;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.mapper.BookCategoryDTOMapper;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookCategoryResource {
    private final CategoryService categoryService;
    private final BookCategoryDTOMapper bookCategoryDTOMapper;

    @RequestMapping(value = "/bookcategory/savecategory/", method = RequestMethod.POST)
    public ResponseEntity<BookCategoryDTO> saveBookCategory(@RequestBody BookCategoryDTO categoryDTO) {
        final BookCategory bookCategory = bookCategoryDTOMapper.toEntity(categoryDTO);
        return ResponseEntity.ok(bookCategoryDTOMapper.toDTO(categoryService.saveCategory(bookCategory)));
    }

    @RequestMapping(value = "/bookcategory/getallcategories/", method = RequestMethod.GET)
    public ResponseEntity<List<BookCategoryDTO>> getAllBookCategories() {
        final List<BookCategoryDTO> allCategories = categoryService.getAllBookCategories()
                .stream()
                .map(bookCategoryDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allCategories);
    }
}
