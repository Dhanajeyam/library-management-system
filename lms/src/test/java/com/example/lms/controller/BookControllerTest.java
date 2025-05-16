package com.example.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lms.model.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Book CRUD Tests")
    class BookCrudTests {

        @Test
        @DisplayName("Should add a book successfully")
        void testAddBook() throws Exception {
            Book book = new Book();
            book.setTitle("Test Book");
            book.setAuthor("Test Author");
            book.setIsbn("ISBN-12345");
            book.setPublishedDate(LocalDate.now());
            book.setAvailableCopies(3);

            mockMvc.perform(post("/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Book added successfully"));
        }

        @ParameterizedTest
        @CsvSource({
                "Java Basics, John Doe, ISBN001",
                "Spring Boot, Mike Lee, ISBN002",
                "Algorithms, Alice Smith, ISBN003"
        })
        @DisplayName("Should add multiple books via parameterized test")
        void testAddBooksParameterized(String title, String author, String isbn) throws Exception {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPublishedDate(LocalDate.now());
            book.setAvailableCopies(5);

            mockMvc.perform(post("/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return all books")
        void testGetAllBooks() throws Exception {
            mockMvc.perform(get("/books"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return 404 for non-existent book")
        void testBookNotFound() throws Exception {
            mockMvc.perform(get("/books/99999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message").value("Book not found with ID: 99999"));
        }
    }
}
