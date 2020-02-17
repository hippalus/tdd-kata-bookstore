package com.bookstore.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRegistrationDTO {
    private Long id;
    private BookStoreDTO bookStore;
    private BookDTO book;

}
