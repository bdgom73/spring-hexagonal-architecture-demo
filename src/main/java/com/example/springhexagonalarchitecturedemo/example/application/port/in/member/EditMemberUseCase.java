package com.example.springhexagonalarchitecturedemo.example.application.port.in.member;

import com.example.springhexagonalarchitecturedemo.example.application.dto.member.request.MemberRequest;

public interface EditMemberUseCase {
    Long save(MemberRequest request);

    Long update(Long id, MemberRequest request);
}
