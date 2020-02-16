package com.bookstore.domain.valueobject;

import com.bookstore.domain.model.Book;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Value
public class BookItems implements Serializable {
    private static final long serialVersionUID = 2488693509273208579L;

    private Set<Book> items;

    private BookItems() {
        items = new HashSet<>();
    }

    private BookItems(Set<Book> bookSet) {
        items = bookSet;
    }

    public static BookItems aNew() {
        return new BookItems();
    }

    public static BookItems of(final Set<Book> bookSet) {
        Preconditions.checkArgument(null != bookSet, "Book items should not be null");
        return new BookItems(bookSet);
    }


    public void add(Book book) {
        Preconditions.checkArgument(null != book, "Book item should not be null");
        this.items.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookItems items1 = (BookItems) o;
        return Objects.equal(items, items1.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(items);
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
