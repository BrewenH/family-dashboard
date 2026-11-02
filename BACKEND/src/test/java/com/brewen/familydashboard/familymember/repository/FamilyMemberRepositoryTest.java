package com.brewen.familydashboard.familymember.repository;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FamilyMemberRepositoryTest {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    @DisplayName("Should save and find a family member")
    void shouldSaveAndFindFamilyMember() {
        // GIVEN
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Brewen");
        member.setColor("BLUE");

        // WHEN
        FamilyMember savedMember = familyMemberRepository.save(member);
        Optional<FamilyMember> foundMember = familyMemberRepository.findById(savedMember.getId());

        // THEN
        assertTrue(foundMember.isPresent());
        assertEquals("Brewen", foundMember.get().getDisplayName());
        assertEquals("BLUE", foundMember.get().getColor());
    }

    @Test
    @DisplayName("Should initialize timestamps on creation")
    void shouldInitializeTimestampsOnCreation() {
        // GIVEN
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Jean");

        // WHEN
        // saveAndFlush force la synchronisation avec la DB et déclenche @PrePersist
        FamilyMember savedMember = familyMemberRepository.saveAndFlush(member);

        // THEN
        assertNotNull(savedMember.getCreatedAt(), "createdAt should not be null");
        assertNotNull(savedMember.getUpdatedAt(), "updatedAt should not be null");
        assertEquals(savedMember.getCreatedAt(), savedMember.getUpdatedAt(),
                "createdAt and updatedAt should be identical at creation");
    }

    @Test
    @DisplayName("Should update updatedAt timestamp on modification")
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        // GIVEN
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Léa");
        FamilyMember savedMember = familyMemberRepository.saveAndFlush(member);
        LocalDateTime firstUpdate = savedMember.getUpdatedAt();

        // WHEN : On modifie le membre
        savedMember.setDisplayName("Léa Modifiée");

        // Petit délai pour s'assurer que la valeur de LocalDateTime.now() change
        Thread.sleep(10);

        FamilyMember updatedMember = familyMemberRepository.saveAndFlush(savedMember);

        // THEN
        assertNotNull(updatedMember.getUpdatedAt());
        assertTrue(updatedMember.getUpdatedAt().isAfter(firstUpdate),
                "updatedAt should be updated after modification");
        assertEquals(savedMember.getCreatedAt(), updatedMember.getCreatedAt(),
                "createdAt should remain unchanged after update");
    }
}
