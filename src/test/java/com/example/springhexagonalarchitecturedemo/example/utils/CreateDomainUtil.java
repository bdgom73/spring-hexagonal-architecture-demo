package com.example.springhexagonalarchitecturedemo.example.utils;

import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;

import java.time.LocalDateTime;

public class CreateDomainUtil {

    public static Member createMember(Long id) {
        return Member.builder()
                .id(id)
                .username("tester_" + id)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public static Team createTeam(Long id) {
        return Team.builder()
                .id(id)
                .name("testTeam_" + id)
                .current(1)
                .total(5)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public static Team createTeam(Long id, int total, int current) {
        return Team.builder()
                .id(id)
                .name("testTeam_" + id)
                .current(current)
                .total(total)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}
