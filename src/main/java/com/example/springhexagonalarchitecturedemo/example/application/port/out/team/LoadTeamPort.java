package com.example.springhexagonalarchitecturedemo.example.application.port.out.team;

import com.example.springhexagonalarchitecturedemo.example.domain.Team;

import java.util.List;
import java.util.Optional;

public interface LoadTeamPort {
    List<Team> loadAll();
    Optional<Team> loadById(Long id);
}
