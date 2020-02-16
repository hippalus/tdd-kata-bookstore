package com.bookstore.domain.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class BookRegistration implements Serializable {
    private static final long serialVersionUID = -1031934950661980057L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    @JsonIgnore
    private Bookstore bookStore;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    public BookRegistration() {
    }
    public BookRegistration(Bookstore bookStore, Book book) {
        this.bookStore=bookStore;
        this.book=book;
    }

    public static BookRegistration bookTo(Book book, Bookstore bookstore) {
        return new BookRegistration(bookstore,book);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRegistration that = (BookRegistration) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(bookStore, that.bookStore) &&
                Objects.equal(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}