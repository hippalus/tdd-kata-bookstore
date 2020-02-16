package com.bookstore.domain.valueobject;

class BookStoreNumberTest extends ValueObjectContractTest{

    private static final String VALUE = "123456789";

    public BookStoreNumberTest() {
        super(BookStoreNumber.of(VALUE), VALUE);
    }
    public static Object[] equalsInstances() {
        return new Object[]{BookStoreNumber.of(VALUE),BookStoreNumber.of(VALUE)};
    }


    public static Object[] nonEqualsInstances() {
        return new Object[]{BookStoreNumber.of("74185263"), BookName.of("oiwjoi-s5465w-wws5")};
    }
}