package com.brewen.familydashboard.familymember.model;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMemberTest {

    @Test
    void shouldBeActiveByDefault() {
        FamilyMember member = new FamilyMember();

        assertTrue(member.isActive());
    }

    @Test
    void shouldExposeFamilyMemberData() {
        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 21, 12, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 10, 21, 12, 30);

        FamilyMember member = new FamilyMember();

        member.setId(1L);
        member.setDisplayName("Brewen");
        member.setColor("BLUE");
        member.setActive(false);
        member.setCreatedAt(createdAt);
        member.setUpdatedAt(updatedAt);

        assertEquals(1L, member.getId());
        assertEquals("Brewen", member.getDisplayName());
        assertEquals("BLUE", member.getColor());
        assertFalse(member.isActive());
        assertEquals(createdAt, member.getCreatedAt());
        assertEquals(updatedAt, member.getUpdatedAt());
    }

    @Test
    void shouldDefineJpaMapping() throws NoSuchFieldException {
        assertNotNull(
                FamilyMember.class.getAnnotation(Entity.class)
        );

        Table table = FamilyMember.class.getAnnotation(Table.class);

        assertNotNull(table);
        assertEquals("family_members", table.name());

        Field idField = FamilyMember.class.getDeclaredField("id");

        assertNotNull(idField.getAnnotation(Id.class));
        assertNotNull(idField.getAnnotation(GeneratedValue.class));

        Field displayNameField =
                FamilyMember.class.getDeclaredField("displayName");

        Column displayNameColumn =
                displayNameField.getAnnotation(Column.class);

        assertNotNull(displayNameColumn);
        assertEquals("display_name", displayNameColumn.name());
        assertFalse(displayNameColumn.nullable());
        assertEquals(50, displayNameColumn.length());

        Field colorField =
                FamilyMember.class.getDeclaredField("color");

        Column colorColumn = colorField.getAnnotation(Column.class);

        assertNotNull(colorColumn);
        assertEquals("color", colorColumn.name());
        assertTrue(colorColumn.nullable());

        Field activeField =
                FamilyMember.class.getDeclaredField("active");

        Column activeColumn = activeField.getAnnotation(Column.class);

        assertNotNull(activeColumn);
        assertEquals("active", activeColumn.name());
        assertFalse(activeColumn.nullable());

        Field createdAtField =
                FamilyMember.class.getDeclaredField("createdAt");

        Column createdAtColumn =
                createdAtField.getAnnotation(Column.class);

        assertNotNull(createdAtColumn);
        assertEquals("created_at", createdAtColumn.name());
        assertFalse(createdAtColumn.nullable());
        assertFalse(createdAtColumn.updatable());

        Field updatedAtField =
                FamilyMember.class.getDeclaredField("updatedAt");

        Column updatedAtColumn =
                updatedAtField.getAnnotation(Column.class);

        assertNotNull(updatedAtColumn);
        assertEquals("updated_at", updatedAtColumn.name());
        assertFalse(updatedAtColumn.nullable());
    }

    @Test
    void shouldInitializeTimestampsBeforePersisting() {
        FamilyMember member = new FamilyMember();

        assertNull(member.getCreatedAt());
        assertNull(member.getUpdatedAt());

        member.initializeTimestamps();

        assertNotNull(member.getCreatedAt());
        assertNotNull(member.getUpdatedAt());
        assertEquals(
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    @Test
    void shouldUpdateOnlyUpdatedAtBeforeUpdating() {
        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 21, 10, 0);

        LocalDateTime previousUpdatedAt =
                LocalDateTime.now(ZoneOffset.UTC)
                        .minusSeconds(1);

        FamilyMember member = new FamilyMember();

        member.setCreatedAt(createdAt);
        member.setUpdatedAt(previousUpdatedAt);

        member.updateTimestamp();

        assertEquals(createdAt, member.getCreatedAt());

        assertTrue(
                member.getUpdatedAt().isAfter(previousUpdatedAt)
        );
    }
}