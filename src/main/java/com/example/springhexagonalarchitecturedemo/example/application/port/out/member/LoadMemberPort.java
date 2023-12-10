package com.example.springhexagonalarchitecturedemo.example.application.port.out.member;


import com.example.springhexagonalarchitecturedemo.example.domain.Member;

import java.util.List;
import java.util.Optional;

public interface LoadMemberPort {
    List<Member> loadAll();
    Optional<Member> loadById(Long id);
    List<Member> loadByTeam(Long teamId);
}
