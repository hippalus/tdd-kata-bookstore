package com.bookstore.application.mapper;

import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.dto.BookRegistrationDTO;
import com.bookstore.application.dto.BookStoreDTO;
import com.bookstore.application.dto.CityDTO;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookRegistration;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.valueobject.BookStoreNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookStoreDTOMapper {

    private final CityDTOMapper cityDTOMapper;

    private final BookDTOMapper bookDTOMapper;

    private final BookRegistrationDTOMapper bookRegistrationDTOMapper;

    @PostConstruct
    public void init() {
        bookRegistrationDTOMapper.setBookDTOMapper(this.bookDTOMapper);
        cityDTOMapper.setBookStoreDTOMapper(this);
    }

    public Bookstore toEntity(BookStoreDTO bookStoreDTO){
        BookStoreNumber bookId = Objects.nonNull(bookStoreDTO.getId()) ?
                BookStoreNumber.of(bookStoreDTO.getId()) :
                BookStoreNumber.next();

        final Set<Book> bookItems = bookStoreDTO.getBookItems()
                .stream()
                .map(bookDTOMapper::toEntity)
                .collect(Collectors.toSet());

        final Set<BookRegistration> bookPriceByBookstoreCities = bookStoreDTO.getBookPriceByBookstoreCities()
                .stream()
                .map(bookRegistrationDTOMapper::toEntity)
                .collect(Collectors.toSet());

        return Bookstore.builder()
                .id(bookId)
                .city(cityDTOMapper.toEntity(bookStoreDTO.getCity()))
                .bookItems(bookItems)
                .bookPriceByBookstoreCities(bookPriceByBookstoreCities)
                .build();
    }

    public BookStoreDTO toDTO(Bookstore bookStore) {

        final Set<BookDTO> bookItems = bookStore.getBookItems()
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toSet());
        Set<BookRegistrationDTO> bookPriceByBookstoreCities= new HashSet<>();
        if (Objects.nonNull(bookStore.getBookPriceByBookstoreCities())) {
            bookPriceByBookstoreCities = bookStore.getBookPriceByBookstoreCities()
                    .stream()
                    .map(bookRegistrationDTOMapper::toDTO)
                    .collect(Collectors.toSet());
        }
        return BookStoreDTO.builder()
                .id(bookStore.getId().getValue())
                .city(CityDTO.builder()
                        .id(bookStore.getCity().getId().getValue())
                        .cityName(bookStore.getCity().getCityName().getValue())
                        .build())
                .bookItems(bookItems)
                .bookPriceByBookstoreCities(bookPriceByBookstoreCities)
                .build();
    }
}
