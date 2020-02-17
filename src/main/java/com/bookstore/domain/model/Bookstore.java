package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookStoreNumber;
import com.bookstore.domain.valueobject.Money;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter@ToString
@EqualsAndHashCode(exclude = {"bookItems","bookPriceByBookstoreCities"})
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="city_id")
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
        book.addBookByBookstore(registration);

    }
    public void removeBook(Book book) {
        bookPriceByBookstoreCities.remove(new BookRegistration(this,book));
        bookItems.remove(book);
    }

}
