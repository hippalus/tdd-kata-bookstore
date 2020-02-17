package com.bookstore.application.mapper;

import com.bookstore.application.dto.BookRegistrationDTO;
import com.bookstore.domain.model.BookRegistration;
import org.springframework.stereotype.Component;



@Component
public class BookRegistrationDTOMapper {

    private BookDTOMapper bookDTOMapper;
    private BookStoreDTOMapper bookStoreDTOMapper;

    public void setBookStoreDTOMapper(BookStoreDTOMapper bookStoreDTOMapper) {
        this.bookStoreDTOMapper = bookStoreDTOMapper;
    }
    public void setBookDTOMapper(BookDTOMapper bookDTOMapper) {
        this.bookDTOMapper = bookDTOMapper;
    }

    public BookRegistration toEntity(BookRegistrationDTO registrationDTO){
        return BookRegistration.builder()
                .id(registrationDTO.getId())
                .book(bookDTOMapper.toEntity(registrationDTO.getBook()))
                .bookStore(bookStoreDTOMapper.toEntity(registrationDTO.getBookStore()))
                .build();
    }

    public BookRegistrationDTO toDTO(BookRegistration bookRegistration) {
        return null;
    }
}
