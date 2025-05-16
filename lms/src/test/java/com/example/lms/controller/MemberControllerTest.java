package com.example.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lms.model.Member;
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
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Member API Tests")
    class MemberTests {

        @Test
        void testAddMember() throws Exception {
            Member member = new Member();
            member.setName("John Doe");
            member.setPhone("9876543210");
            member.setRegisteredDate(LocalDate.now());

            mockMvc.perform(post("/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(member)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Member added successfully"));
        }

        @ParameterizedTest
        @CsvSource({
                "Alice, 9998887777",
                "Bob, 6665554444",
                "Charlie, 1234567890"
        })
        void testAddMultipleMembers(String name, String phone) throws Exception {
            Member member = new Member();
            member.setName(name);
            member.setPhone(phone);
            member.setRegisteredDate(LocalDate.now());

            mockMvc.perform(post("/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(member)))
                    .andExpect(status().isOk());
        }

        @Test
        void testGetAllMembers() throws Exception {
            mockMvc.perform(get("/members"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }
}
