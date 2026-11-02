package com.brewen.familydashboard.familymember.repository;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Optional<FamilyMember> findByDisplayName(String displayName);
    // Les méthodes de base (save, findById, findAll, deleteById) sont héritées de JpaRepository
}

