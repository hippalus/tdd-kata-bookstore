package com.bookstore.application.mapper;

import com.bookstore.application.dto.CityDTO;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.CityName;
import com.bookstore.domain.valueobject.CityNumber;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CityDTOMapper {

    private  BookStoreDTOMapper bookStoreDTOMapper;

    public void setBookStoreDTOMapper(BookStoreDTOMapper bookStoreDTOMapper) {
        this.bookStoreDTOMapper = bookStoreDTOMapper;
    }

    public City toEntity(CityDTO cityDTO) {
        CityNumber cityId = Objects.nonNull(cityDTO.getId()) ?
                CityNumber.of(cityDTO.getId()) :
                CityNumber.next();

        final Set<Bookstore> bookstores = cityDTO.getBookstore()
                .stream()
                .map(bookStoreDTOMapper::toEntity)
                .collect(Collectors.toSet());

        return City.builder()
                .id(cityId)
                .cityName(CityName.of(cityDTO.getCityName()))
                .bookstore(bookstores)
                .build();
    }
}
