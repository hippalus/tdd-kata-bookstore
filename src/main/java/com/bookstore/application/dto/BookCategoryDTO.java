package com.bookstore.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class BookCategoryDTO {
    private String id;
    private String name;
    @Builder.Default
    private Set<BookDTO> bookItems = new HashSet<>();
}
