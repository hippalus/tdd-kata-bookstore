package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
@Value
public class CityName implements Serializable {
    private static final long serialVersionUID = 4196122803783836459L;

    private final String value;

    public CityName(String value) {
        this.value = value;
    }

    public static CityName of(String value) {
        Preconditions.checkArgument(null != value, "City name should not be null");
        Preconditions.checkArgument(!value.isEmpty(), "City name should not be empty");
        return new CityName(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
