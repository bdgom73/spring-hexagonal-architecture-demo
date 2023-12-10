package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.adapter;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member.MemberRepository;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamEntity;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamMapper;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamRepository;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.EditTeamPort;
import com.example.springhexagonalarchitecturedemo.example.application.port.out.team.LoadTeamPort;
import com.example.springhexagonalarchitecturedemo.example.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamPersistenceAdapter implements LoadTeamPort, EditTeamPort {

   private final TeamRepository teamRepository;
   private final MemberRepository memberRepository;

    @Override
    public List<Team> loadAll() {
        return teamRepository.findAll().stream()
                .map(TeamMapper::entityYoDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Team> loadById(Long id) {
        return teamRepository.findById(id).stream()
                .map(TeamMapper::entityYoDomain)
                .findFirst();
    }

    @Override
    public Team save(Team team) {
        TeamEntity entity = teamRepository.save(TeamMapper.domainToEntity(team));
        return TeamMapper.entityYoDomain(entity);
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public void update(Team team) {
        TeamEntity entity = teamRepository.findById(team.getId())
                .orElseThrow();
        entity.update(team.getName(), team.getTotal());
    }
}
