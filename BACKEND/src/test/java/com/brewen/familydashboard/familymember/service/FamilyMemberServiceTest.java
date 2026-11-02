package com.brewen.familydashboard.familymember.service;

import com.brewen.familydashboard.familymember.dto.FamilyMemberRequest;
import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.exception.FamilyMemberNotFoundException;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import com.brewen.familydashboard.familymember.model.FamilyMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("Should save family member and return response")
    void shouldSaveFamilyMember() {
        // GIVEN
        FamilyMemberRequest request = new FamilyMemberRequest("Jean", "BLUE");
        FamilyMember savedMember = new FamilyMember();
        savedMember.setId(1L);
        savedMember.setDisplayName("Jean");
        savedMember.setColor("BLUE");
        savedMember.setActive(true);
        savedMember.setCreatedAt(LocalDateTime.now());
        savedMember.setUpdatedAt(LocalDateTime.now());

        when(familyMemberRepository.save(any(FamilyMember.class))).thenReturn(savedMember);

        // WHEN
        FamilyMemberResponse response = familyMemberService.saveFamilyMember(request);

        // THEN
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Jean", response.displayName());
        verify(familyMemberRepository, times(1)).save(any(FamilyMember.class));
    }

    @Test
    @DisplayName("Should return list of all members")
    void shouldGetAllMembers() {
        // GIVEN
        FamilyMember member = new FamilyMember();
        member.setId(1L);
        member.setDisplayName("Jean");

        when(familyMemberRepository.findAll()).thenReturn(List.of(member));

        // WHEN
        List<FamilyMemberResponse> responses = familyMemberService.getAllMembers();

        // THEN
        assertEquals(1, responses.size());
        assertEquals("Jean", responses.getFirst().displayName());
        verify(familyMemberRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return member when ID exists")
    void shouldGetMemberByIdSuccess() {
        // GIVEN
        Long id = 1L;
        FamilyMember member = new FamilyMember();
        member.setId(id);
        member.setDisplayName("Jean");

        when(familyMemberRepository.findById(id)).thenReturn(Optional.of(member));

        // WHEN
        FamilyMemberResponse response = familyMemberService.getMemberById(id);

        // THEN
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Jean", response.displayName());
    }

    @Test
    @DisplayName("Should throw exception when member ID does not exist")
    void shouldThrowExceptionWhenMemberNotFound() {
        // GIVEN
        Long id = 999L;
        when(familyMemberRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(FamilyMemberNotFoundException.class, () -> familyMemberService.getMemberById(id));
    }
}
