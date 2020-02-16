package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.Money;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Bookstore implements Serializable {
    private static final long serialVersionUID = -5392911380617017176L;
    @Id
    private BookStoreNumber id;
    @JsonIgnore
    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Book> bookItems=new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private City city;
    @Builder.Default

    @OneToMany(mappedBy = "bookStore",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<BookRegistration> bookPriceByBookstoreCities = new HashSet<>();

    public void addBook(Book book, Money newPrice) {
        Book bookByNewPrice=book.changeBookPrice(newPrice);
        bookPriceByBookstoreCities.add(new BookRegistration(this,bookByNewPrice));
        bookItems.add(bookByNewPrice);
    }
    public void addBook(Book book) {
        BookRegistration registration=new BookRegistration(this,book);
        bookPriceByBookstoreCities.add(registration);
        bookItems.add(book);
        book.addsaj(registration);

    }
    public void removeBook(Book book) {
        bookPriceByBookstoreCities.remove(new BookRegistration(this,book));
        bookItems.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookstore bookstore = (Bookstore) o;
        return Objects.equal(id, bookstore.id) &&
                Objects.equal(bookItems, bookstore.bookItems) &&
                Objects.equal(city, bookstore.city) &&
                Objects.equal(bookPriceByBookstoreCities, bookstore.bookPriceByBookstoreCities);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, bookItems, city, bookPriceByBookstoreCities);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id",id)
                .add("bookItems",bookItems)
                .add("city",city)
                .add("bookPriceByBookstoreCities",bookPriceByBookstoreCities)
                .toString();
    }


}
