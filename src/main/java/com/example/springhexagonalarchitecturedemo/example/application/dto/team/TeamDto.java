package com.example.springhexagonalarchitecturedemo.example.application.dto.team;

import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TeamDto {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.createdDate = team.getCreatedDate();
        this.lastModifiedDate = team.getLastModifiedDate();
    }
}
