package com.bookstore.domain.valueobject;

import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;

@Value
public class Money implements Serializable {
    private static final long serialVersionUID = 7860440040721382817L;

    private final Double value;

    private Money(Double value) {
        this.value = value;
    }

    public static Money of(Double value) {
        Preconditions.checkArgument(null != value, "Money should no be null");
        Preconditions.checkArgument(value >= 0, "Money should be greater than zero");
        return new Money(value);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
