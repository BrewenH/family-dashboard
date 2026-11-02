package com.brewen.familydashboard.familymember.exception;

public class FamilyMemberNotFoundException extends RuntimeException {
    public FamilyMemberNotFoundException(Long id) {
        super("Family member not found with id: " + id);
    }
}
