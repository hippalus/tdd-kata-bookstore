package com.bookstore.application.rest;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.dto.BookStoreDTO;
import com.bookstore.application.dto.CityDTO;
import com.bookstore.application.mapper.BookDTOMapper;
import com.bookstore.application.mapper.BookStoreDTOMapper;
import com.bookstore.domain.model.Bookstore;
import com.bookstore.domain.service.BookService;
import com.bookstore.domain.service.BookstoreService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BookstoreResourceTest {
    @Autowired
    private BookstoreResource bookstoreResource;
    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private BookStoreDTOMapper bookStoreDTOMapper;
    @Autowired
    private BookDTOMapper bookDTOMapper;
    @Autowired
    private BookService bookService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void contextLoads() {
        assertThat(bookstoreResource).isNotNull();
    }

    @Test
    void should_save_bookstore_and_return_200OK() throws Exception {

        //given
        BookStoreDTO bookStoreDTO = newBookStoreDTO("Mephisto");
        String inputJson = TestUtils.pojoToJson(bookStoreDTO);
        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/bookstore/savebookstore/")
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
    void should_get_all_bookstore() throws Exception {
        //given:
        BookStoreDTO bookStoreDTO1 = newBookStoreDTO("Mephisto");
        bookstoreService.saveBookStore(bookStoreDTOMapper.toEntity(bookStoreDTO1));
        List<String> expected = new ArrayList<>();
        expected.add(TestUtils.pojoToJson(bookStoreDTO1));

        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/bookstore/getallbookstore/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected.toString());
    }

    @Test
    void should_remove_book() throws Exception {
        //given
        BookStoreDTO bookStoreDTO = newBookStoreDTO("Mephisto");
        bookstoreService.saveBookStore(bookStoreDTOMapper.toEntity(bookStoreDTO));
        Bookstore expectedBookstore = bookStoreDTOMapper.toEntity(bookStoreDTO);
        expectedBookstore.removeBook(bookDTOMapper.toEntity(newBookDTO()));
        String expectedContent = TestUtils.pojoToJson(bookStoreDTOMapper.toDTO(expectedBookstore));
        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/bookstore/removebook/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("bookstoreId", "Mephisto")
                .param("bookId", "123456"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expectedContent);

    }
    private BookStoreDTO newBookStoreDTO(String id) {
        Set<BookDTO> bookItems = new HashSet<>();
        bookItems.add(newBookDTO());
        return BookStoreDTO.builder()
                .id(id)
                .city(CityDTO.builder()
                        .id("34")
                        .cityName("Istanbul")
                        .build())
                .bookItems(bookItems)
                .build();
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

}
