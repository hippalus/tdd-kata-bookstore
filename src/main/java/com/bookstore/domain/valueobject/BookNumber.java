package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class BookNumber implements Serializable {
    private static final long serialVersionUID = -3428172804271607334L;

    private final String value;

    private BookNumber(String value) {
        this.value = value;
    }

    public static BookNumber of(String value) {
        Preconditions.checkArgument(null != value);
        return new BookNumber(value);
    }

    public static BookNumber next() {
        return new BookNumber(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return getValue();
    }
}