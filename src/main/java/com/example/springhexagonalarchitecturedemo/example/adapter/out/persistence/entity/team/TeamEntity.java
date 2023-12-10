package com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.team;

import com.example.springhexagonalarchitecturedemo.example.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "team")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "total")
    @ColumnDefault("0")
    private Integer total;
    @Column(name = "current")
    @ColumnDefault("0")
    private Integer current;

    @Builder
    public TeamEntity(Long id, String name, Integer total, Integer current) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.current = current;
    }

    public void update(String name, Integer total) {
        this.name = name;
        this.total = total;
    }
}
