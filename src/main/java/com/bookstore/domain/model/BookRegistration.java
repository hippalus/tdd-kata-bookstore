package com.bookstore.domain.model;
import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
public class BookRegistration implements Serializable {
    private static final long serialVersionUID = -1031934950661980057L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    private Bookstore bookStore;

    @ManyToOne
    @JoinColumn(name = "book_id")
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("bookStore", bookStore)
                .add("book", book)
                .toString();
    }
}