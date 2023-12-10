package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.member;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.BaseEntity;
import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team.TeamEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "member")
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Builder
    public MemberEntity(Long id, String username, TeamEntity team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    public void update(String username){
        this.username = username;
    }
}
