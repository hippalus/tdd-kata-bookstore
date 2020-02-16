package com.bookstore.domain.valueobject;


import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class BookStoreNumber implements Serializable {
    private static final long serialVersionUID = 682661294044772984L;
    private final String value;

    private BookStoreNumber(String value) {
        this.value = value;
    }

    public static BookStoreNumber of(String value) {
        Preconditions.checkArgument(null != value);
        return new BookStoreNumber(value);
    }

    public static BookStoreNumber next() {
        return new BookStoreNumber(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
