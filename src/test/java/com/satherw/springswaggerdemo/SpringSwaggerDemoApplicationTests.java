package com.satherw.springswaggerdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint.NONE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc(print = NONE)
@SpringBootTest
class SpringSwaggerDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBooks() throws Exception {
        // given there are two books known to the app
        mockMvc.perform(
                get("/books")
        )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("*", hasSize(2)));
        // when we add a third book
        mockMvc.perform(post("/books").header("content-type", "application/json").content("""
                {
                        "name": "Refactoring (Second Edition)",
                        "author": "Martin Fowler"
                }
                """)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // then there should be three books
        mockMvc.perform(
                        get("/books")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("*", hasSize(3)));
    }
}
