package com.bookstore.domain.service;

import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.valueobject.CategoryName;
import com.bookstore.domain.valueobject.CategoryNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void context_load() {
        assertThat(categoryService).isNotNull();
    }
    @Test
    void should_create_category() {
        //given:
        BookCategory bookCategory = BookCategory.builder()
                .id(CategoryNumber.of("123456789"))
                .name(CategoryName.of("Romance"))
                .build();

        //when:
        final BookCategory persistedCategory = categoryService.saveCategory(bookCategory);
        //then:
        assertThat(persistedCategory).isEqualTo(bookCategory);
    }
    @Test
    void should_get_all_category_list(){
        //given:
        BookCategory bookCategory1 = BookCategory.builder()
                .id(CategoryNumber.of("123456789"))
                .name(CategoryName.of("Romance"))
                .build();
        BookCategory bookCategory2 = BookCategory.builder()
                .id(CategoryNumber.of("12345"))
                .name(CategoryName.of("Programming"))
                .build();
        final BookCategory persistedCategory1 = categoryService.saveCategory(bookCategory1);
        final BookCategory persistedCategory2 = categoryService.saveCategory(bookCategory2);
        List<BookCategory> bookCategoryList=new ArrayList<>();
        bookCategoryList.add(persistedCategory1);
        bookCategoryList.add(persistedCategory2);

        //when:
        final List<BookCategory> allBookCategories = categoryService.getAllBookCategories();

        //then:
        assertThat(allBookCategories).isEqualTo(bookCategoryList);

    }
}
