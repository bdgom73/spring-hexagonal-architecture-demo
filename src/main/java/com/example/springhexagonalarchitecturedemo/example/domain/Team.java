package com.example.springhexagonalarchitecturedemo.example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Team {
    private Long id;
    private String name;
    private Integer total;
    private Integer current;
    private List<Member> members;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public Team(Long id, String name, Integer total, Integer current, List<Member> members, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.current = current;
        this.members = !ObjectUtils.isEmpty(members) ? members : new ArrayList<>();
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }


    public void changeTotal(Integer total) {
        this.total = total;
    }

    public void join() {
        current++;
    }

    public void leave() {
        current--;
    }
}
