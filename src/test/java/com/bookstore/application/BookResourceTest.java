package com.bookstore.application;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.dto.BookStoreDTO;
import com.bookstore.application.dto.CityDTO;
import com.bookstore.application.mapper.BookDTOMapper;
import com.bookstore.application.mapper.BookStoreDTOMapper;
import com.bookstore.domain.model.Book;
import com.bookstore.domain.model.BookCategory;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookService;
import com.bookstore.domain.service.BookstoreService;
import com.bookstore.domain.service.CategoryService;
import com.bookstore.domain.valueobject.CategoryName;
import com.bookstore.domain.valueobject.CategoryNumber;
import com.bookstore.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BookResourceTest {
    @Autowired
    private BookResource bookResource;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private BookDTOMapper bookDTOMapper;
    @Autowired
    private BookStoreDTOMapper bookstoreMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void contextLoads() {
        assertThat(bookResource).isNotNull();
    }

    @Test
    void should_save_book_and_return_200OK() throws Exception {

        //given
        BookDTO bookDTO = newBookDTO();
        String inputJson = TestUtils.pojoToJson(bookDTO);
        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/book/savebook/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(inputJson);
    }


    @Test
    void should_get_all_books() throws Exception {

        //given
        BookDTO bookDTO = newBookDTO();
        bookService.saveBook(bookDTOMapper.toEntity(bookDTO));
        List<String> expected = new ArrayList<>();
        expected.add(TestUtils.pojoToJson(bookDTO));


        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/book/getallbooks/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected.toString());
    }

    @Test
    void should_get_books_by_category() throws Exception {
        //given
        categoryService.saveCategory(BookCategory.builder()
                .id(CategoryNumber.of("123456"))
                .name(CategoryName.of("Programming"))
                .build());
        BookDTO bookDTO = newBookDTO();
        bookService.saveBook(bookDTOMapper.toEntity(bookDTO));
        List<String> expected = new ArrayList<>();
        expected.add(TestUtils.pojoToJson(bookDTO));


        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/book/getbooksbycategory/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("categoryId", "123456"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected.toString());
    }

    @Test
    void should_change_book_category() throws Exception {
        //given
        categoryService.saveCategory(BookCategory.builder()
                .id(CategoryNumber.of("123456"))
                .name(CategoryName.of("Programming"))
                .build());
        BookDTO bookDTO = newBookDTO();
        bookService.saveBook(bookDTOMapper.toEntity(bookDTO));

        categoryService.saveCategory(BookCategory.builder()
                .id(CategoryNumber.of("7890"))
                .name(CategoryName.of("Design Patterns"))
                .build());
        List<String> expected = new ArrayList<>();
        expected.add(TestUtils.pojoToJson(bookDTO));


        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/book/changebookcategory/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("bookId", "123456")
                .param("categoryId", "7890"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isNotEqualTo(expected.toString());

    }
    @Test
    void should_add_book_to_bookstore() throws Exception {
        //given
        categoryService.saveCategory(BookCategory.builder()
                .id(CategoryNumber.of("123456"))
                .name(CategoryName.of("Programming"))
                .build());
        BookDTO bookDTO = newBookDTO();
        Book addedBook = bookService.saveBook(bookDTOMapper.toEntity(bookDTO));

        BookStoreDTO bookStoreDTO= newBookStoreDTO();
        final Bookstore bookstore = bookstoreService.saveBookStore(bookstoreMapper.toEntity(bookStoreDTO));
        addedBook.toBookStore(bookstore);

        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/book/addbooktobookstore/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("bookId", "123456")
                .param("bookstoreId", "Mephisto"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isNotEqualTo(TestUtils.pojoToJson(addedBook));

    }


    private BookDTO newBookDTO() {
        return BookDTO.builder()
                .id("123456")
                .name("Head First Design Patten")
                .category(BookCategoryDTO.builder()
                        .id("123456")
                        .name("Programming")
                        .build())
                .price(49.99)
                .build();
    }


    private BookCategoryDTO newCategoryDTO() {
        Set<BookDTO> bookDTOS = new HashSet<>();
        bookDTOS.add(newBookDTO());
        return BookCategoryDTO.builder()
                .id("123456")
                .name("Programming")
                .bookItems(bookDTOS)
                .build();
    }

    private BookStoreDTO newBookStoreDTO(){
        return BookStoreDTO.builder()
                .id("Mephisto")
                .city(CityDTO.builder()
                        .id("34")
                        .cityName("Istanbul")
                        .build())
                .build();
    }
}
