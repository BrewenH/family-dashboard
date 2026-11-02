package com.brewen.familydashboard.familymember.mapper;

import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.model.FamilyMember;

public class FamilyMemberMapper {

    private FamilyMemberMapper() {} // Empêche l'instanciation

    public static FamilyMemberResponse toResponse(FamilyMember member) {
        if (member == null) return null;

        return new FamilyMemberResponse(
                member.getId(),
                member.getDisplayName(),
                member.getColor(),
                member.isActive(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
