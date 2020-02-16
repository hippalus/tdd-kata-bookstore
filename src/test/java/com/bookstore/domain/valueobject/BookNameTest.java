package com.bookstore.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class BookNameTest extends ValueObjectContractTest {

    private static final String TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE = "Test Driven Development: By Example";

    public BookNameTest() {
        super(BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE), TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE);
    }
    public static Object[] equalsInstances() {
        return new Object[]{BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE),BookName.of(TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE)};
    }


    public static Object[] nonEqualsInstances() {
        return new Object[]{ BookName.of("Domain Driven Design"), BookName.of("Clean Code")};
    }
    @Test
    void book_name_should_not_be_null(){
        assertThatIllegalArgumentException()
                .isThrownBy(()-> BookName.of(null))
                .withMessage("Book name should not be null");
    }

    @Test
    void book_name_should_not_be_empty(){
        assertThatIllegalArgumentException()
                .isThrownBy(()-> BookName.of(""))
                .withMessage("Book name should not be empty");
    }

}