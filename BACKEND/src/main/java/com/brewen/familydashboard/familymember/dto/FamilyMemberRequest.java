package com.brewen.familydashboard.familymember.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FamilyMemberRequest(
        @NotBlank(message = "Le nom d'affichage est obligatoire")
        @Size(max = 50, message = "Le nom d'affichage ne doit pas dépasser 50 caractères")
        String displayName,

        String color
) {}
