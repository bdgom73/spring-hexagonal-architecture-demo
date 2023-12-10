package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member.MemberMapper;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;

public class TeamMapper {

    public static TeamEntity domainToEntity(Team team) {
        return TeamEntity.builder()
                .id(team.getId())
                .name(team.getName())
                .total(team.getTotal())
                .current(team.getCurrent())
                .build();
    }

    public static Team entityYoDomain(TeamEntity entity) {
        return Team.builder()
                .id(entity.getId())
                .name(entity.getName())
                .total(entity.getTotal())
                .current(entity.getCurrent())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

}
