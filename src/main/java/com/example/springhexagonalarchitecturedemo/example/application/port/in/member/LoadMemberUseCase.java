package com.example.springhexagonalarchitecturedemo.example.application.port.in.member;

import com.example.springhexagonalarchitecturedemo.example.application.dto.member.MemberDto;

import java.util.List;

public interface LoadMemberUseCase {
    List<MemberDto> getMembers();
}
