package com.brewen.familydashboard.familymember.repository;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    // Les méthodes de base (save, findById, findAll, deleteById) sont héritées de JpaRepository
}

