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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName("Should return a list of all family members")
    void shouldReturnAllFamilyMembers() throws Exception {
        // GIVEN : On simule une liste de membres retournée par le service
        FamilyMemberResponse member1 = new FamilyMemberResponse(
                1L, "Jean", "BLUE", true, LocalDateTime.now(), LocalDateTime.now());
        FamilyMemberResponse member2 = new FamilyMemberResponse(
                2L, "Léa", "PINK", true, LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(service.getAllMembers()).thenReturn(java.util.List.of(member1, member2));

        // WHEN & THEN : On appelle l'endpoint et on vérifie le résultat
        mockMvc.perform(get("/api/family-members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].displayName").value("Jean"))
                .andExpect(jsonPath("$[1].displayName").value("Léa"));
    }

    @Test
    @DisplayName("Should return a family member when ID exists")
    void shouldReturnMemberWhenIdExists() throws Exception {
        // GIVEN
        Long memberId = 1L;
        FamilyMemberResponse mockResponse = new FamilyMemberResponse(
                memberId, "Brewen", "BLUE", true, LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(service.getMemberById(memberId)).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/api/family-members/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId))
                .andExpect(jsonPath("$.displayName").value("Brewen"));
    }

    @Test
    @DisplayName("Should return 404 when family member does not exist")
    void shouldReturnNotFoundWhenMemberDoesNotExist() throws Exception {
        // GIVEN
        Long memberId = 999L;

        // Correction : On lève l'exception métier spécifique, pas une RuntimeException générique
        Mockito.when(service.getMemberById(memberId))
                .thenThrow(new com.brewen.familydashboard.familymember.exception.FamilyMemberNotFoundException(memberId));

        // WHEN & THEN
        mockMvc.perform(get("/api/family-members/{id}", memberId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("Should return 400 when displayName is blank")
    void shouldReturnBadRequestWhenDisplayNameIsBlank() throws Exception {
        // GIVEN : Un JSON avec un nom vide
        String invalidJson = """
            {
                "displayName": "",
                "color": "BLUE"
            }
            """;

        // WHEN & THEN
        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should return 400 when displayName is too long")
    void shouldReturnBadRequestWhenDisplayNameIsTooLong() throws Exception {
        // GIVEN : Un nom de plus de 50 caractères
        String longName = "a".repeat(51);
        String invalidJson = String.format("""
            {
                "displayName": "%s",
                "color": "BLUE"
            }
            """, longName);

        // WHEN & THEN
        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should return 500 when an unexpected error occurs")
    void shouldReturn500WhenUnexpectedErrorOccurs() throws Exception {
        // GIVEN : On simule une erreur totalement imprévue (ex: panne serveur)
        Mockito.when(service.getAllMembers()).thenThrow(new RuntimeException("Database crash!"));

        // WHEN & THEN
        mockMvc.perform(get("/api/family-members"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }


}

