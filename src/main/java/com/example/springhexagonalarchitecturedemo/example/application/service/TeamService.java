package com.example.springhexagonalarchitecturedemo.example.application.service;

import com.example.springhexagonalarchitecturedemo.example.application.dto.team.TeamDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.team.reqeust.TeamRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.DeleteTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.EditTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.JoinTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.LoadTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.EditMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.LoadMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.EditTeamPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.LoadTeamPort;
import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService
    implements LoadTeamUseCase,
        EditTeamUseCase,
        DeleteTeamUseCase,
        JoinTeamUseCase {

    private final LoadTeamPort loadTeamPort;
    private final EditTeamPort editTeamPort;
    private final LoadMemberPort loadMemberPort;
    private final EditMemberPort editMemberPort;

    @Override
    public List<TeamDto> getTeams() {
        return loadTeamPort.loadAll().stream()
                .map(TeamDto::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Long save(TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .total(request.getTotal())
                .current(1)
                .build();
        Team saveTeam = editTeamPort.save(team);
        return saveTeam.getId();
    }

    @Override
    @Transactional
    public Long update(Long id, TeamRequest request) {
        Team team = getTeam(id);

        if (request.getTotal() < team.getCurrent()) {
            throw new IllegalArgumentException("가입 인원이 더 많아 변경이 불가능 합니다");
        }

        team.changeTotal(request.getTotal());
        editTeamPort.update(team);
        return team.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        editTeamPort.delete(id);
    }

    @Override
    @Transactional
    public void join(Long teamId, Long memberId) {
        Team team = getTeam(teamId);
        if (team.getTotal() <= team.getCurrent()) {
            throw new IllegalArgumentException("최대 가입 가능 인원은 " + team.getTotal() + "명 입니다");
        }
        Member member = getMember(memberId);

        member.changeTeam(team);
        team.join();

        editTeamPort.save(team);
        editMemberPort.save(member);
    }

    @Override
    @Transactional
    public void leave(Long teamId, Long memberId) {
        Team team = getTeam(teamId);
        Member member = getMember(memberId);

        if (!member.getTeam().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 팀에 가입 되어있지 않습니다");
        }

        member.changeTeam(null);
        team.leave();

        editTeamPort.save(team);
        editMemberPort.save(member);
    }

    private Member getMember(Long memberId) {
        return loadMemberPort.loadById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));
    }

    private Team getTeam(Long teamId) {
        return loadTeamPort.loadById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));
    }
}
