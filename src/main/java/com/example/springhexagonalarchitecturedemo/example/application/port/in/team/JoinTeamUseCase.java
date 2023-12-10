package com.example.springhexagonalarchitecturedemo.example.application.port.in.team;

public interface JoinTeamUseCase {
    void join(Long teamId, Long memberId);
    void leave(Long teamId, Long memberId);
}
