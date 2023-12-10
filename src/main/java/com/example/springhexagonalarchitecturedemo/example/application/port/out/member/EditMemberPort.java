package com.example.springhexagonalarchitecturedemo.example.application.port.out.member;

import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;

public interface EditMemberPort {
    Member save(Member member);
    void delete(Long id);
    void changeTeam(Long id, Team team);
    void update(Member member);
}
