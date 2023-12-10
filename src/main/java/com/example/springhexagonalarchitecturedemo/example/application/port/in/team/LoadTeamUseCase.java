package com.example.springhexagonalarchitecturedemo.example.application.port.in.team;

import com.example.springhexagonalarchitecturedemo.example.application.dto.team.TeamDto;

import java.util.List;

public interface LoadTeamUseCase {
    List<TeamDto> getTeams();
}
