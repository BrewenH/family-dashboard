package com.brewen.familydashboard.familymember.dto;

import java.time.LocalDateTime;

public record FamilyMemberResponse(
        Long id,
        String displayName,
        String color,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
