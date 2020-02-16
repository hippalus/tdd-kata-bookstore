package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.CityName;
import com.bookstore.domain.valueobject.CityNumber;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class City implements Serializable {
    private static final long serialVersionUID = -2944424750055282611L;

    @Id
    private CityNumber id;
    private CityName cityName;
}
