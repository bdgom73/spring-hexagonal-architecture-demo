package com.example.springhexagonalarchitecturedemo.example.adapter.in.api;

import com.example.springhexagonalarchitecturedemo.example.adapter.in.dto.ApiResponse;
import com.example.springhexagonalarchitecturedemo.example.application.dto.team.TeamDto;
import com.example.springhexagonalarchitecturedemo.example.application.dto.team.reqeust.TeamRequest;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.EditTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.JoinTeamUseCase;
import com.example.springhexagonalarchitecturedemo.example.application.port.in.team.LoadTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamApiController {

    private final LoadTeamUseCase loadTeamUseCase;
    private final EditTeamUseCase editTeamUseCase;
    private final JoinTeamUseCase joinTeamUseCase;

    @GetMapping("/api/teams")
    public ResponseEntity<ApiResponse<List<TeamDto>>> getTeams() {
        List<TeamDto> teams = loadTeamUseCase.getTeams();
        return ResponseEntity.ok(ApiResponse.ok(teams));
    }

    @PostMapping("/api/teams")
    public ResponseEntity<ApiResponse<Long>> saveTeam(@RequestBody TeamRequest request) {
        Long teamId = editTeamUseCase.save(request);
        return ResponseEntity.ok(ApiResponse.ok(teamId));
    }

    @PutMapping("/api/teams/{id}")
    public ResponseEntity<ApiResponse<Long>> editTeam(
            @PathVariable("id") Long id,
            @RequestBody TeamRequest request
    ) {
        Long teamId = editTeamUseCase.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok(teamId));
    }

    @PostMapping("/api/teams/{id}/join")
    public ResponseEntity<ApiResponse<Object>> join(
            @PathVariable("id") Long id,
            @RequestParam("memberId") Long memberId
    ) {
        joinTeamUseCase.join(id, memberId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/api/teams/{id}/leave")
    public ResponseEntity<ApiResponse<Object>> leave(
            @PathVariable("id") Long id,
            @RequestParam("memberId") Long memberId
    ) {
        joinTeamUseCase.leave(id, memberId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
