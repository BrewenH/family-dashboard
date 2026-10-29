package com.brewen.familydashboard.familymember.service;

import com.brewen.familydashboard.familymember.dto.FamilyMemberRequest;
import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.mapper.FamilyMemberMapper;
import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    @Transactional
    public FamilyMemberResponse saveFamilyMember(FamilyMemberRequest request) {
        FamilyMember member = new FamilyMember();
        member.setDisplayName(request.displayName());
        member.setColor(request.color());
        // active est true par défaut dans l'entité

        FamilyMember savedMember = familyMemberRepository.save(member);
        return FamilyMemberMapper.toResponse(savedMember);
    }

    @Transactional(readOnly = true)
    public java.util.List<FamilyMemberResponse> getAllMembers() {
        return familyMemberRepository.findAll()
                .stream()
                .map(FamilyMemberMapper::toResponse)
                .toList();
    }

}
