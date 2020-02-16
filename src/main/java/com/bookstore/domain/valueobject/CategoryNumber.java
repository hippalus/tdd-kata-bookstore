package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class CategoryNumber implements Serializable {
    private static final long serialVersionUID = -3181210006577835180L;
    private final String value;

    private CategoryNumber(String value) {
        this.value = value;
    }

    public static CategoryNumber of(String value) {
        Preconditions.checkArgument(null != value);
        return new CategoryNumber(value);
    }

    public static CategoryNumber next() {
        return new CategoryNumber(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
