package com.example.springhexagonalarchitecturedemo.example.application.port.in.team;

import com.example.springhexagonalarchitecturedemo.example.application.dto.team.TeamDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.team.reqeust.TeamRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.EditMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.LoadMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.EditTeamPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.LoadTeamPort;
import com.example.springhexagonalarchitecturedemo.example.application.service.TeamService;
import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.springhexagonalarchitecturedemo.example.utils.CreateDomainUtil.createMember;
import static com.example.springhexagonalarchitecturedemo.example.utils.CreateDomainUtil.createTeam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("팀 서비스 로직 테스트")
class TeamUseCaseTest {
    @Mock
    private LoadTeamPort loadTeamPort;
    @Mock
    private  EditTeamPort editTeamPort;
    @Mock
    private LoadMemberPort loadMemberPort;
    @Mock
    private  EditMemberPort editMemberPort;
    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("[성공] 팀 전체 목록 조회")
    void loadTeam() {
        // given
        given( loadTeamPort.loadAll() )
                .willReturn(
                        new ArrayList<>(
                                IntStream.range(0, 10).mapToObj((i) -> createTeam(i + 1L)).toList()
                        )
                );
        // when
        List<TeamDto> teams = teamService.getTeams();

        // then
        assertThat(teams.size()).isEqualTo(10);
        assertThat(teams.get(0).getClass()).isEqualTo(TeamDto.class);
    }

    @Test
    @DisplayName("[성공] 팀 생성")
    void saveTeam() {
        // given
        Team team = createTeam(1L);
        given( editTeamPort.save(any()) )
                .willReturn( team );
        TeamRequest request = new TeamRequest("testTeam_1", 4);
        // when
        Long teamId = teamService.save(request);
        // then
        verify(editTeamPort, times(1)).save(any());
        assertThat(teamId).isEqualTo(1L);
    }

    @Test
    @DisplayName("[성공] 팀 수정")
    void updateTeam() {
        // given
        Team team = createTeam(1L);
        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        TeamRequest request = new TeamRequest("testTeam_1", 4);
        // when
        Long teamId = teamService.update(anyLong(), request);
        // then
        verify(editTeamPort, times(1)).update(any());
        assertThat(teamId).isEqualTo(team.getId());
    }

    @Test
    @DisplayName("[에러] 팀 수정 -> 가입 인원 > 총 인원")
    void updateTeamEx1() {
        // given
        Team team = createTeam(1L);
        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        TeamRequest request = new TeamRequest("testTeam_1", 0);

        // when then
        assertThatThrownBy(
                () -> teamService.update(anyLong(), request)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가입 인원이 더 많아 변경이 불가능 합니다");

        verify(loadTeamPort, times(1)).loadById(any());
    }


    @Test
    @DisplayName("[성공] 팀 해체")
    void deleteTeam() {
        // given when
       teamService.delete(anyLong());
        // then
        verify(editTeamPort, times(1)).delete(any());
    }

    @Test
    @DisplayName("[성공] 팀 가입")
    void joinTeam() {
        // given
        Team team = createTeam(1L);
        Member member = createMember(1L);

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        given( loadMemberPort.loadById(anyLong()) )
                .willReturn( Optional.of(member) );

        // when
        teamService.join(team.getId() , member.getId());

        // then
        verify(editTeamPort, times(1)).save(any());
        verify(editMemberPort, times(1)).save(any());
    }

    @Test
    @DisplayName("[에러] 팀 가입 -> 가입 인원 초과")
    void joinTeamEx1() {
        // given
        Team team = createTeam(1L, 5,5);
        Member member = createMember(1L);

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        // when then
        assertThatThrownBy(
                () -> teamService.join(team.getId() , member.getId())
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 가입 가능 인원은 " + team.getTotal() + "명 입니다");

        verify(editTeamPort, times(0)).save(any());
        verify(editMemberPort, times(0)).save(any());

    }

    @Test
    @DisplayName("[성공] 팀 떠나기")
    void leaveTeam() {
        // given
        Team team = createTeam(1L);
        Member member = createMember(1L);
        member.setTeam(team);

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        given( loadMemberPort.loadById(anyLong()) )
                .willReturn( Optional.of(member) );

        // when
        teamService.leave(team.getId() , member.getId());

        // then
        verify(editTeamPort, times(1)).save(any());
        verify(editMemberPort, times(1)).save(any());
    }

    @Test
    @DisplayName("[에러] 팀 떠나기 -> 해당팀 가입x")
    void leaveTeamEx1() {
        // given
        Team team = createTeam(1L);
        Member member = createMember(1L);
        member.setTeam( createTeam(2L) );

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of(team) );

        given( loadMemberPort.loadById(anyLong()) )
                .willReturn( Optional.of(member) );

        // when then
        assertThatThrownBy(
                () ->  teamService.leave(team.getId() , member.getId())
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 팀에 가입 되어있지 않습니다");

        verify(editTeamPort, times(0)).save(any());
        verify(editMemberPort, times(0)).save(any());
    }
}