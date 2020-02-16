package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;

@Value
public class CategoryName implements Serializable {
    private static final long serialVersionUID = 7513635986988591335L;

    private final String value;

    private CategoryName(String value) {
        this.value = value;
    }

    public static CategoryName of(String value) {
        Preconditions.checkArgument(null != value, "Category name should not be null");
        Preconditions.checkArgument(!value.isEmpty(), "Category name should not be empty");
        return new CategoryName(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
