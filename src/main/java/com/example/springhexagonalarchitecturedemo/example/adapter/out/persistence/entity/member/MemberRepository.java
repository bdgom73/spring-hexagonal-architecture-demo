package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT m FROM MemberEntity m WHERE m.team.id = :teamId")
    List<MemberEntity> findByTeam(@Param("teamId") Long teamId);
}
