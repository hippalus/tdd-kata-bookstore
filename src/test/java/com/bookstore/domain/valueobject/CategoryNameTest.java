package com.bookstore.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class CategoryNameTest extends ValueObjectContractTest {

    private static final String SOFTWARE = "Software";

    public CategoryNameTest() {
        super(CategoryName.of(SOFTWARE), SOFTWARE);
    }

    public static Object[] equalsInstances() {
        return new Object[]{CategoryName.of(SOFTWARE), CategoryName.of(SOFTWARE)};
    }


    public static Object[] nonEqualsInstances() {
        return new Object[]{CategoryName.of("Hardware"), CategoryName.of("Computer")};
    }
    @Test
    void category_name_should_not_be_null(){
        assertThatIllegalArgumentException()
                .isThrownBy(()-> CategoryName.of(null))
                .withMessage("Category name should not be null");
    }

    @Test
    void book_name_should_not_be_empty(){
        assertThatIllegalArgumentException()
                .isThrownBy(()-> CategoryName.of(""))
                .withMessage("Category name should not be empty");
    }
}