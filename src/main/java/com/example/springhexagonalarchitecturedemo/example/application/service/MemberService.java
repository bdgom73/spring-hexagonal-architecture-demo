package com.example.springhexagonalarchitecturedemo.example.application.service;

import com.example.springhexagonalarchitecturedemo.example.application.dto.member.MemberDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.member.request.MemberRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.member.DeleteMemberUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.member.EditMemberUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.member.LoadMemberUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.EditMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.LoadMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.LoadTeamPort;
import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService
        implements LoadMemberUseCase,
        EditMemberUseCase,
        DeleteMemberUseCase {


    private final LoadMemberPort loadMemberPort;
    private final EditMemberPort editMemberPort;
    private final LoadTeamPort loadTeamPort;

    @Override
    public List<MemberDto> getMembers() {
        return loadMemberPort.loadAll().stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(MemberRequest request) {

        Team team = null;
        if (!ObjectUtils.isEmpty(request.getTeamId())) {
            team = loadTeamPort.loadById(request.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 팀 입니다"));
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .team(team)
                .build();

        return editMemberPort.save(member).getId();
    }

    @Override
    @Transactional
    public Long update(Long id, MemberRequest request) {
        Member member = loadMemberPort.loadById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));

        if (ObjectUtils.isEmpty(request.getTeamId())) {
            member.changeTeam(null);
        } else {
            if (!member.getTeam().getId()
                    .equals(request.getTeamId())) {
                Team team = loadTeamPort.loadById(request.getTeamId())
                        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 팀 입니다"));
                member.changeTeam(team);
            }
        }

        member.changeUsername(request.getUsername());
        editMemberPort.update(member);
        return member.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        editMemberPort.delete(id);
    }
}
