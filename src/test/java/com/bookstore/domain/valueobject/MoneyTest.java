package com.bookstore.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MoneyTest extends ValueObjectContractTest{

    public MoneyTest() {
        super(Money.of(165.45), "165.45");
    }
    public static Object[] equalsInstances() {
        return new Object[]{Money.of(165.45),Money.of(165.45)};
    }


    public static Object[] nonEqualsInstances() {
        return new Object[]{ Money.of(12.5),Money.of(89.99)};
    }
    @Test
    void money_should_be_greater_than_zero(){
        assertThatIllegalArgumentException().isThrownBy(()->Money.of(-15.8)).withMessage("Money should be greater than zero");
    }
    @Test
    void money_should_not_be_null(){
        assertThatIllegalArgumentException().isThrownBy(()->Money.of(null)).withMessage("Money should no be null");
    }
}