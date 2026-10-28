package com.brewen.familydashboard.familymember.service;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository repository;

    @Transactional
    public FamilyMember saveFamilyMember(FamilyMember member) {
        return repository.save(member);
    }
}
