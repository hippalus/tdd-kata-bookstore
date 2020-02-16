package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookItems;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Set;

@Converter
public class BookItemsConverter implements AttributeConverter<BookItems, Set<Book>> {
    @Override
    public Set<Book> convertToDatabaseColumn(BookItems attribute) {
        return attribute.getItems();
    }

    @Override
    public BookItems convertToEntityAttribute(Set<Book> dbData) {
        return BookItems.of(dbData);
    }
}
