package com.bookstore.application.rest;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.dto.BookStoreDTO;
import com.bookstore.application.dto.CityDTO;
import com.bookstore.application.mapper.BookDTOMapper;
import com.bookstore.application.mapper.BookStoreDTOMapper;
import com.bookstore.domain.model.BookRegistration;
import com.bookstore.domain.service.BookRegistrationService;
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
public class BookRegistrationResourceTest {

    @Autowired
    private BookRegistrationResource bookRegistrationResource;
    @Autowired
    private BookRegistrationService bookRegistrationService;
    @Autowired
    private BookStoreDTOMapper bookStoreDTOMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private BookService bookService;

    private MockMvc mvc;
    @Autowired
    private BookDTOMapper bookDTOMapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void contextLoads() {
        assertThat(bookRegistrationResource).isNotNull();
    }

    @Test
    void should_get_books_by_bookstore() throws Exception {
        //given:
        BookStoreDTO bookStoreDTO = newBookStoreDTO("R&W");
        final BookRegistration bookRegistration = bookRegistrationService.bookRegistration(BookRegistration.builder()
                .book(bookDTOMapper.toEntity(newBookDTO()))
                .bookStore(bookStoreDTOMapper.toEntity(bookStoreDTO))
                .build());

        List<BookDTO> expectedList=new ArrayList<>();
        expectedList.add(bookDTOMapper.toDTO(bookRegistration.getBook()));
        final String expected = TestUtils.pojoToJson(expectedList);

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/bookregistration/getbooksbybookstore/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("bookstoreId", "R&W"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected);

    }
    @Test
    void  should_throwsException_books_by_category_and_bookstore() throws Exception {
        //given:
        BookStoreDTO bookStoreDTO = newBookStoreDTO("R&W");
        final BookRegistration bookRegistration = bookRegistrationService.bookRegistration(BookRegistration.builder()
                .book(bookDTOMapper.toEntity(newBookDTO()))
                .bookStore(bookStoreDTOMapper.toEntity(bookStoreDTO))
                .build());

        List<BookDTO> expectedList=new ArrayList<>();
        expectedList.add(bookDTOMapper.toDTO(bookRegistration.getBook()));
        final String expected = TestUtils.pojoToJson(expectedList);

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/bookregistration/getbooksbycategoryandbookstore/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("bookstoreId", "R&W")
                .param("categoryId", "123456"))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Book could not found according to specified category:123456 and bookstore:R&W");

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
                .name("Head First Design Patten")
                .category(BookCategoryDTO.builder()
                        .name("Programming")
                        .build())
                .price(49.99)
                .build();
    }
}
