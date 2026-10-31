package com.brewen.familydashboard.familymember.controller;

import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.service.FamilyMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FamilyMemberController.class)
class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyMemberService service;

    // --- Tests pour POST /api/family-members ---

    @Test
    @DisplayName("Should create a family member and return 201 Created")
    void shouldCreateFamilyMember() throws Exception {
        String memberJson = """
            {
                "displayName": "Brewen",
                "color": "BLUE"
            }
            """;
        FamilyMemberResponse mockResponse = new FamilyMemberResponse(
                1L, "Brewen", "BLUE", true, LocalDateTime.now(), LocalDateTime.now());

        when(service.saveFamilyMember(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.displayName").value("Brewen"));
    }

    @Test
    @DisplayName("Should return 400 when displayName is blank")
    void shouldReturnBadRequestWhenDisplayNameIsBlank() throws Exception {
        String invalidJson = "{\"displayName\": \"\", \"color\": \"BLUE\"}";

        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    // --- Tests pour GET /api/family-members ---

    @Test
    @DisplayName("Should return a list of all family members")
    void shouldReturnAllFamilyMembers() throws Exception {
        FamilyMemberResponse m1 = new FamilyMemberResponse(1L, "Jean", "BLUE", true, LocalDateTime.now(), LocalDateTime.now());
        FamilyMemberResponse m2 = new FamilyMemberResponse(2L, "Léa", "PINK", true, LocalDateTime.now(), LocalDateTime.now());

        when(service.getAllMembers()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/family-members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].displayName").value("Jean"));
    }

    // --- Tests pour GET /api/family-members/{id} ---

    @Test
    @DisplayName("Should return a family member when ID exists")
    void shouldReturnMemberWhenIdExists() throws Exception {
        Long id = 1L;
        FamilyMemberResponse mockResponse = new FamilyMemberResponse(
                id, "Brewen", "BLUE", true, LocalDateTime.now(), LocalDateTime.now());

        when(service.getMemberById(id)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/family-members/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.displayName").value("Brewen"));
    }

    @Test
    @DisplayName("Should return 404 when family member does not exist")
    void shouldReturnNotFoundWhenMemberDoesNotExist() throws Exception {
        Long id = 999L;
        when(service.getMemberById(id)).thenThrow(new com.brewen.familydashboard.familymember.exception.FamilyMemberNotFoundException(id));

        mockMvc.perform(get("/api/family-members/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
