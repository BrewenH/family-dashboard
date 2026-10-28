package com.brewen.familydashboard.familymember.controller;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/family-members")
@RequiredArgsConstructor
public class FamilyMemberController {

    private final FamilyMemberService service;

    @PostMapping
    public ResponseEntity<FamilyMember> createFamilyMember(@RequestBody FamilyMember member) {
        FamilyMember savedMember = service.saveFamilyMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }
}
