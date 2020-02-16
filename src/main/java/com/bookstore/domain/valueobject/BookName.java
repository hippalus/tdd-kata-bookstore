package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;

@Value
public class BookName implements Serializable {
    private static final long serialVersionUID = 1472848255421949864L;

    private final String value;

    private BookName(String value) {
        this.value = value;
    }

    public static BookName of(String value) {
        Preconditions.checkArgument(null != value, "Book name should not be null");
        Preconditions.checkArgument(!value.isEmpty(), "Book name should not be empty");
        return new BookName(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
