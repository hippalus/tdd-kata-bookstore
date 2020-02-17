package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.CategoryName;
import com.bookstore.domain.valueobject.CategoryNumber;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "bookItems")
@Builder
@Getter@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class BookCategory implements Serializable {
    private static final long serialVersionUID = 2912880139331227869L;
    @Id
    private CategoryNumber id;

    private CategoryName name;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Book> bookItems = new HashSet<>();

}
