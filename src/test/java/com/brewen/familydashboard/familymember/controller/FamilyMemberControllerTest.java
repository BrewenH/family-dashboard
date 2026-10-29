package com.brewen.familydashboard.familymember.controller;

import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.service.FamilyMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FamilyMemberController.class)
class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyMemberService service;

    @Test
    @DisplayName("Should create a family member and return 201 Created")
    void shouldCreateFamilyMember() throws Exception {
        // GIVEN
        String memberJson = """
            {
                "displayName": "Brewen",
                "color": "BLUE"
            }
            """;

        // Le service retourne maintenant un FamilyMemberResponse (DTO) et non plus l'entité
        FamilyMemberResponse mockResponse = new FamilyMemberResponse(
                1L,
                "Brewen",
                "BLUE",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Mockito.when(service.saveFamilyMember(any())).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.displayName").value("Brewen"))
                .andExpect(jsonPath("$.color").value("BLUE"));
    }
}

