package com.brewen.familydashboard.familymember.controller;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.service.FamilyMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        // GIVEN : On prépare un JSON et on simule la réponse du service
        String memberJson = """
                {
                    "displayName": "Jean",
                    "color": "#FFFFFF",
                    "active": true
                }
                """;

        FamilyMember savedMember = new FamilyMember();
        savedMember.setId(1L);
        savedMember.setDisplayName("Jean");
        savedMember.setColor("#FFFFFF");

        when(service.saveFamilyMember(any(FamilyMember.class))).thenReturn(savedMember);

        // WHEN : On effectue l'appel HTTP POST
        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                // THEN : On vérifie le statut HTTP et le contenu du JSON retourné
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.displayName").value("Jean"));
    }
}
