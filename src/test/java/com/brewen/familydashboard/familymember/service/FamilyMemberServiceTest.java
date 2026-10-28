package com.brewen.familydashboard.familymember.service;

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
    private FamilyMemberRepository repository;

    @InjectMocks
    private FamilyMemberService service;

    @Test
    @DisplayName("Should save a family member successfully")
    void shouldSaveFamilyMember() {
        // GIVEN : On prépare les données et on simule le comportement du repository
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Jean");
        member.setColor("#FFFFFF");

        FamilyMember savedMember = new FamilyMember();
        savedMember.setId(1L);
        savedMember.setDisplayName("Jean");
        savedMember.setColor("#FFFFFF");

        // On dit à Mockito : "Quand le repository reçoit n'importe quel FamilyMember, retourne savedMember"
        when(repository.save(any(FamilyMember.class))).thenReturn(savedMember);

        // WHEN : On appelle la méthode du service
        FamilyMember result = service.saveFamilyMember(member);

        // THEN : On vérifie que le résultat est correct et que le repository a bien été appelé
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jean", result.getDisplayName());
        verify(repository, times(1)).save(member);
    }
}
