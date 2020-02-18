package com.bookstore.application.rest;

import com.bookstore.application.dto.BookCategoryDTO;
import com.bookstore.application.dto.BookDTO;
import com.bookstore.application.mapper.BookCategoryDTOMapper;
import com.bookstore.domain.service.CategoryService;
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

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class BookCategoryResourceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookCategoryDTOMapper categoryDTOMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void should_save_book_category() throws Exception {
        //given
        BookCategoryDTO bookCategoryDTO = newCategoryDTO();
        String inputJson = TestUtils.pojoToJson(bookCategoryDTO);
        //when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/bookcategory/savecategory/")
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
    void should_get_all_book_categories() {
    }

    private BookCategoryDTO newCategoryDTO() {
        return BookCategoryDTO.builder()
                .id("12356")
                .name("Programming")
                .build();
    }
    private BookDTO newBookDTO() {
        return BookDTO.builder()
                .id("123456")
                .name("Head First Design Patten")
                .price(49.99)
                .build();
    }
}