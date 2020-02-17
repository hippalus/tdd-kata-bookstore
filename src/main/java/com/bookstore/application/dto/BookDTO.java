package com.bookstore.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class BookDTO {
    private String id;
    private String name;
    private BookCategoryDTO category;
    private Double price;
    @Builder.Default
    private Set<BookRegistrationDTO> bookByBookstore=new HashSet<>();
}
