package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookName;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.Money;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = 2330454441648787594L;

    @Id
    private BookNumber id;
    private BookName name;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "category_id")
    private BookCategory category;
    private Money price;
    @Builder.Default
    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private  Set<BookRegistration> bookByBookstore = new HashSet<>();

    public void addsaj(BookRegistration bookRegistration) {
        bookByBookstore.add(bookRegistration);
    }

    public void toBookStore(Bookstore bookstore) {
        bookByBookstore.add(BookRegistration.bookTo(this, bookstore));
    }

    public Book changeBookPrice(Money price) {
        return new Book(this.id, this.name, this.category, price, this.bookByBookstore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equal(id, book.id) &&
                Objects.equal(name, book.name) &&
                Objects.equal(category, book.category) &&
                Objects.equal(price, book.price) &&
                Objects.equal(bookByBookstore, book.bookByBookstore);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, category, price, bookByBookstore);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("category", category)
                .add("price", price)
                .add("bookByBookstore", bookByBookstore)
                .toString();
    }
}
