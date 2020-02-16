package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookItems;
import com.bookstore.domain.valueobject.BookStoreNumber;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookStore implements Serializable {
    private static final long serialVersionUID = -5392911380617017176L;

    private BookStoreNumber id;
    private BookItems bookItems;

}
