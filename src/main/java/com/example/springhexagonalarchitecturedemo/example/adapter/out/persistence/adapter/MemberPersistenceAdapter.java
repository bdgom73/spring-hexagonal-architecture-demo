package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.adapter;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member.MemberEntity;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member.MemberMapper;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member.MemberRepository;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamMapper;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.EditMemberPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.member.LoadMemberPort;
import com.example.springhexagonalarchitecturedemo.example.domain.Member;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter
        implements
        LoadMemberPort,
        EditMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> loadAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::entityYoDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Member> loadById(Long id) {
        return memberRepository.findById(id)
                .stream()
                .map(MemberMapper::entityYoDomainWithTeam)
                .findFirst();
    }

    @Override
    public List<Member> loadByTeam(Long teamId) {
        return memberRepository.findByTeam(teamId)
                .stream()
                .map(MemberMapper::entityYoDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Member save(Member entity) {
        MemberEntity memberEntity = memberRepository.save(MemberMapper.domainToEntity(entity));
        return MemberMapper.entityYoDomain(memberEntity);
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public void changeTeam(Long id, Team team) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));
        memberEntity.setTeam(TeamMapper.domainToEntity(team));
    }

    @Override
    public void update(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));
        memberEntity.update(member.getUsername());
        memberEntity.setTeam(TeamMapper.domainToEntity(member.getTeam()));
    }
}
