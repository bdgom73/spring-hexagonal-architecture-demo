package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamMapper;
import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import org.springframework.util.ObjectUtils;

public class MemberMapper {

    public static MemberEntity domainToEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .username(member.getUsername())
                .team(!ObjectUtils.isEmpty(member.getTeam()) ? TeamMapper.domainToEntity(member.getTeam()) : null)
                .build();
    }

    public static Member entityYoDomain(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    public static Member entityYoDomainWithTeam(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .team(ObjectUtils.isEmpty(entity.getTeam()) ? null : TeamMapper.entityYoDomain(entity.getTeam()))
                .build();
    }

}
