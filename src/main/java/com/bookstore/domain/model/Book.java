package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookName;
import com.bookstore.domain.valueobject.BookNumber;
import com.bookstore.domain.valueobject.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EqualsAndHashCode(exclude = "bookByBookstore")
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="bookstore_id")
    private Set<Bookstore> bookstores=new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private  Set<BookRegistration> bookByBookstore = new HashSet<>();

    public void addBookByBookstore(BookRegistration bookRegistration) {
        bookByBookstore.add(bookRegistration);
    }

    public void toBookStore(Bookstore bookstore) {
        bookByBookstore.add(BookRegistration.bookTo(this, bookstore));
    }

    public Book changeBookPrice(Money price) {
        return new Book(this.id, this.name, this.category, price,this.bookstores, this.bookByBookstore);
    }
}
