package com.bookstore.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class CityDTO {
    private String id;
    private String cityName;
    @Builder.Default
    private Set<BookStoreDTO> bookstore = new HashSet<>();
}
