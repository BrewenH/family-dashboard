package com.brewen.familydashboard.familymember.service;

import com.brewen.familydashboard.familymember.dto.FamilyMemberRequest;
import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FamilyMemberServiceTest {

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @InjectMocks
    private FamilyMemberService familyMemberService;

    @Test
    @DisplayName("Should save a family member and return a response DTO")
    void shouldSaveFamilyMember() {
        // GIVEN
        FamilyMemberRequest request = new FamilyMemberRequest("Jean", "BLUE");

        FamilyMember savedMember = new FamilyMember();
        savedMember.setId(1L);
        savedMember.setDisplayName("Jean");
        savedMember.setColor("BLUE");
        savedMember.setActive(true);

        // On simule le repository qui sauvegarde l'entité
        when(familyMemberRepository.save(any(FamilyMember.class))).thenReturn(savedMember);

        // WHEN
        FamilyMemberResponse result = familyMemberService.saveFamilyMember(request);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Jean", result.displayName());
        assertEquals("BLUE", result.color());
        assertTrue(result.active());

        verify(familyMemberRepository, times(1)).save(any(FamilyMember.class));
    }
}
