# spring-hexagonal-architecture-demo
spring-hexagonal-architecture-demo

```text
com
    └─example
        └─springhexagonalarchitecturedemo
            │  SpringHexagonalArchitectureDemoApplication.java
            │
            ├─example
            │  ├─adapter
            │  │  ├─in
            │  │  │  ├─api
            │  │  │  │      MemberApiController.java
            │  │  │  │      TeamApiController.java
            │  │  │  │
            │  │  │  └─dto
            │  │  │          ApiResponse.java
            │  │  │
            │  │  └─out
            │  │      └─persistence
            │  │          ├─adapter
            │  │          │      MemberPersistenceAdapter.java
            │  │          │      TeamPersistenceAdapter.java
            │  │          │
            │  │          └─entity
            │  │              │  BaseEntity.java
            │  │              │
            │  │              ├─member
            │  │              │      MemberEntity.java
            │  │              │      MemberMapper.java
            │  │              │      MemberRepository.java
            │  │              │
            │  │              └─team
            │  │                      TeamEntity.java
            │  │                      TeamMapper.java
            │  │                      TeamRepository.java
            │  │
            │  ├─application
            │  │  ├─dto
            │  │  │  ├─member
            │  │  │  │  │  MemberDto.java
            │  │  │  │  │
            │  │  │  │  └─request
            │  │  │  │          MemberRequest.java
            │  │  │  │
            │  │  │  └─team
            │  │  │      │  TeamDto.java
            │  │  │      │
            │  │  │      └─reqeust
            │  │  │              TeamRequest.java
            │  │  │
            │  │  ├─port
            │  │  │  ├─in
            │  │  │  │  ├─member
            │  │  │  │  │      DeleteMemberUseCase.java
            │  │  │  │  │      EditMemberUseCase.java
            │  │  │  │  │      LoadMemberUseCase.java
            │  │  │  │  │
            │  │  │  │  └─team
            │  │  │  │          DeleteTeamUseCase.java
            │  │  │  │          EditTeamUseCase.java
            │  │  │  │          JoinTeamUseCase.java
            │  │  │  │          LoadTeamUseCase.java
            │  │  │  │
            │  │  │  └─out
            │  │  │      ├─member
            │  │  │      │      EditMemberPort.java
            │  │  │      │      LoadMemberPort.java
            │  │  │      │
            │  │  │      └─team
            │  │  │              EditTeamPort.java
            │  │  │              LoadTeamPort.java
            │  │  │
            │  │  └─service
            │  │          MemberService.java
            │  │          TeamService.java
            │  │
            │  └─domain
            │          Member.java
            │          Team.java
            │
            └─global
                └─config
                        JpaConfig.java
```

## API

### 회원 조회
```http request
GET /api/members HTTP/1.1
Host: localhost
```

### 회원 추가
```http request
POST /api/members HTTP/1.1
Host: localhost
Content-Type: application/json

{
   "username" : "example_user",
   "teamId" : 1
}
```

### 회원 수정
```http request
PUT /api/members/{id} HTTP/1.1
Host: localhost
Content-Type: application/json

{
   "username" : "example_update_user",
   "teamId" : 1
}
```

### 팀 조회
```http request
GET /api/teams HTTP/1.1
Host: localhost
```

### 팀 추가
```http request
POST /api/teams HTTP/1.1
Host: localhost
Content-Type: application/json

{
   "name" : "teamA",
   "total" : 5
}
```

### 팀 수정
```http request
PUT /api/teams/{id} HTTP/1.1
Host: localhost
Content-Type: application/json

{
   "name" : "teamA_next",
   "total" : 5
}
```