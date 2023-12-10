package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
