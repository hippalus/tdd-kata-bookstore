package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class CityNumber implements Serializable {
    private static final long serialVersionUID = -1125626547690818687L;

    private final String value;

    private CityNumber(String value) {
        this.value = value;
    }

    public static CityNumber of(String value) {
        Preconditions.checkArgument(null != value);
        return new CityNumber(value);
    }

    public static CityNumber next() {
        return new CityNumber(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
