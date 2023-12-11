package com.example.springhexagonalarchitecturedemo.example.application.port.in.member;

import com.example.springhexagonalarchitecturedemo.example.application.dto.member.MemberDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.member.request.MemberRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.EditMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.LoadMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.LoadTeamPort;
import com.example.springhexagonalarchitecturedemo.example.application.service.MemberService;
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
@DisplayName("회원 서비스 로직 테스트")
class MemberUseCaseTest {

    @Mock
    private LoadMemberPort loadMemberPort;
    @Mock
    private EditMemberPort editMemberPort;
    @Mock
    private LoadTeamPort loadTeamPort;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("[성공] 전체 회원 목록 조회")
    void loadAllMembers() {
        // given
        given( loadMemberPort.loadAll() )
                .willReturn(
                        new ArrayList<>(
                                IntStream.range(0, 10).mapToObj((i) -> createMember(i + 1L)).toList()
                        )
                );

        // when
        List<MemberDto> members = memberService.getMembers();

        // then
        assertThat(members.size()).isEqualTo(10);
        assertThat(MemberDto.class).isEqualTo(members.get(0).getClass());
    }

    @Test
    @DisplayName("[성공] 회원 추가")
    void saveMember() {
        // given
        Member member = createMember(1L);
        given( editMemberPort.save( any() ) )
                .willReturn( member );

        // when
        Long memberId = memberService.save(new MemberRequest("tester_1", null));

        // then
        assertThat(memberId).isEqualTo( member.getId() );
    }

    @Test
    @DisplayName("[에러] 회원 추가 -> 찾을 수 없는 팀")
    void saveMemberEx1() {
        // given
        given( loadTeamPort.loadById( anyLong() ) )
                .willReturn( Optional.empty() );

        // when then
        assertThatThrownBy(
                () -> memberService.save(new MemberRequest("tester_1", 1L))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("찾을 수 없는 팀 입니다");
    }

    @Test
    @DisplayName("[성공] 회원 수정 - 회원명 수정")
    void updateMemberEx1() {
        // given
        Member member = createMember(1L);
        Team team = createTeam(1L);
        member.changeTeam( team );

        given( loadMemberPort.loadById( anyLong() ) )
                .willReturn( Optional.of(member) );

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.of( team ) );

        // when
        memberService.update(1L, new MemberRequest("tester_change_1", 2L));

        // then
        verify(editMemberPort, times(1)).update(any());
    }

    @Test
    @DisplayName("[에러] 회원 수정 -> 팀 변경시 >> 찾을 수 없는 팀")
    void updateMember() {
        // given
        Member member = createMember(1L);
        member.changeTeam( createTeam(1L) );

        given( loadMemberPort.loadById( anyLong() ) )
                .willReturn( Optional.of(member) );

        given( loadTeamPort.loadById(anyLong()) )
                .willReturn( Optional.empty() );

        // when then
        assertThatThrownBy(
                () -> memberService.update(1L, new MemberRequest("tester_change_1", 2L))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("찾을 수 없는 팀 입니다");


    }

    @Test
    @DisplayName("[성공] 회원 삭제")
    void deleteMember() {
        // given

        // when
        memberService.delete(1L);

        // then
        verify(editMemberPort, times(1)).delete(anyLong());
    }

}