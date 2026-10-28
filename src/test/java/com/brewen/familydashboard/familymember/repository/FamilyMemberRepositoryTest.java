package com.brewen.familydashboard.familymember.repository;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FamilyMemberRepositoryTest {

    @Autowired
    private FamilyMemberRepository repository;

    @Test
    @DisplayName("Should save a FamilyMember and find it by ID")
    void shouldSaveAndFindFamilyMember() {
        // Given
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Brewen");
        member.setColor("#00FF00");
        member.setActive(true);

        // When
        FamilyMember savedMember = repository.save(member);
        Optional<FamilyMember> foundMember = repository.findById(savedMember.getId());

        // Then
        assertTrue(foundMember.isPresent(), "Le membre devrait être trouvé");
        assertEquals("Brewen", foundMember.get().getDisplayName());
        assertNotNull(savedMember.getId(), "L'ID devrait avoir été généré");
    }
}
