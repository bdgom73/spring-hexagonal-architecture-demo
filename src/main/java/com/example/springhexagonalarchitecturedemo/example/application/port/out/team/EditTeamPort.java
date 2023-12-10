package com.example.springhexagonalarchitecturedemo.example.application.port.out.team;

import com.example.springhexagonalarchitecturedemo.example.domain.Team;

public interface EditTeamPort {
    Team save(Team team);
    void delete(Long id);

    void update(Team team);
}
