package com.example.springhexagonalarchitecturedemo.example.adapter.in.api;

import com.example.springhexagonalarchitecturedemo.example.adapter.in.dto.ApiResponse;
import com.example.springhexagonalarchitecturedemo.example.application.dto.member.MemberDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.member.request.MemberRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.member.EditMemberUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.member.LoadMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final LoadMemberUseCase loadMemberUseCase;
    private final EditMemberUseCase editMemberUseCase;

    @GetMapping("/api/members")
    public ResponseEntity<ApiResponse<List<MemberDto>>> getMembers() {
        return ResponseEntity.ok(ApiResponse.ok(loadMemberUseCase.getMembers()));
    }

    @PostMapping("/api/members")
    public ResponseEntity<ApiResponse<Object>> saveMember(@RequestBody MemberRequest request) {
        Long memberId = editMemberUseCase.save(request);
        return ResponseEntity.ok(ApiResponse.ok(memberId));
    }

    @PutMapping("/api/members/{id}")
    public ResponseEntity<ApiResponse<Object>> editMember(
            @PathVariable("id") Long id,
            @RequestBody MemberRequest request
    ) {
        Long memberId = editMemberUseCase.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok(memberId));
    }
}
