package com.example.springhexagonalarchitecturedemo.example.application.dto.member;

import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberDto {

    private final Long id;
    private final String username;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.createdDate = member.getCreatedDate();
        this.lastModifiedDate = member.getLastModifiedDate();
    }
}
