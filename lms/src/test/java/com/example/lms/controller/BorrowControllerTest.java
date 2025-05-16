package com.example.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lms.model.Borrow;
import org.junit.jupiter.api.*;
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
class BorrowControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Borrow Tests")
    class BorrowTests {

        @Test
        void testBorrowBookSuccess() throws Exception {
            Borrow borrow = new Borrow();
            borrow.setMemberId(1); // Make sure this exists
            borrow.setBookId(1);   // Make sure this exists and has available copies
            borrow.setBorrowedDate(LocalDate.now());
            borrow.setDueDate(LocalDate.now().plusDays(7));

            mockMvc.perform(post("/borrow/lend")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(borrow)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Book lent successfully"));
        }

        @Test
        void testReturnBookNotFound() throws Exception {
            mockMvc.perform(post("/borrow/return/9999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Borrow record not found with ID: 9999"));
        }

        @Test
        void testGetAllBorrowedBooks() throws Exception {
            mockMvc.perform(get("/borrow"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }
}
