package com.bookstore.domain.model;

import com.bookstore.domain.valueobject.BookItems;
import com.bookstore.domain.valueobject.CategoryName;
import com.bookstore.domain.valueobject.CategoryNumber;
import com.google.common.base.MoreObjects;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class BookCategory implements Serializable {
    private static final long serialVersionUID = 2912880139331227869L;
    @Id
    private CategoryNumber id;
    private CategoryName name;
    @Convert(converter = BookItemsConverter.class, attributeName = "items")
    private BookItems bookItems;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("bookItems", bookItems)
                .toString();
    }
}
