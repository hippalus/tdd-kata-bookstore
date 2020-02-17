package com.bookstore.application.dto;

import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class BookStoreDTO {

    private String id;

    @JsonIgnore
    @Builder.Default
    private Set<BookDTO> bookItems = new HashSet<>();

    private CityDTO city;

    @Builder.Default
    private Set<BookRegistrationDTO> bookPriceByBookstoreCities = new HashSet<>();
}
