package com.example.springhexagonalarchitecturedemo.example.application.port.in.team;

import com.example.springhexagonalarchitecturedemo.example.application.dto.team.reqeust.TeamRequest;

public interface EditTeamUseCase {
    Long save(TeamRequest request);

    Long update(Long id, TeamRequest request);

    void delete(Long id);
}
