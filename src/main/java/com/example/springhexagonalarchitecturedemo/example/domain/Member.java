package com.example.springhexagonalarchitecturedemo.example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Setter
public class Member {

    private Long id;
    private String username;
    private Team team;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public Member(Long id, String username, Team team, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.username = username;
        this.team = team;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeTeam(Team team) {
        this.team = team;
    }
}
