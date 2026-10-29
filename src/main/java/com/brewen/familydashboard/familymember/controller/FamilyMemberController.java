package com.brewen.familydashboard.familymember.controller;

import com.brewen.familydashboard.familymember.dto.FamilyMemberRequest;
import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.service.FamilyMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/family-members")
@RequiredArgsConstructor
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    @PostMapping
    public ResponseEntity<FamilyMemberResponse> createFamilyMember(@Valid @RequestBody FamilyMemberRequest request) {
        FamilyMemberResponse response = familyMemberService.saveFamilyMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<java.util.List<FamilyMemberResponse>> getAllMembers() {
        return ResponseEntity.ok(familyMemberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyMemberResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(familyMemberService.getMemberById(id));
    }


}
