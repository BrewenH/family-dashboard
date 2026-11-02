package com.brewen.familydashboard.familymember.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMemberTest {

    @Test
    @DisplayName("Devrait instancier correctement un FamilyMember et récupérer ses valeurs")
    void shouldCorrectlyInstantiateFamilyMember() {
        // GIVEN : On prépare les données d'entrée (le contexte)
        String displayName = "Jean";
        String color = "#FF0000";
        boolean active = true;
        LocalDateTime now = LocalDateTime.now();

        // WHEN : On exécute l'action (ici, on crée l'objet et on remplit ses champs)
        // ATTENTION : À ce stade, la classe FamilyMember n'existe pas encore !
        FamilyMember member = new FamilyMember();
        member.setId(1L);
        member.setDisplayName(displayName);
        member.setColor(color);
        member.setActive(active);
        member.setCreatedAt(now);
        member.setUpdatedAt(now);

        // THEN : On vérifie que le résultat est conforme aux attentes
        assertEquals(1L, member.getId(), "L'ID doit être identique");
        assertEquals(displayName, member.getDisplayName(), "Le nom doit être identique");
        assertEquals(color, member.getColor(), "La couleur doit être identique");
        assertTrue(member.isActive(), "Le membre doit être actif");
        assertEquals(now, member.getCreatedAt(), "La date de création doit être identique");
        assertEquals(now, member.getUpdatedAt(), "La date de modification doit être identique");
    }
}
