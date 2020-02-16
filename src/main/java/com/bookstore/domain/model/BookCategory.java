package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.CategoryName;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookCategory implements Serializable {
    private static final long serialVersionUID = 2912880139331227869L;

    private CategoryName name;

}
