package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String todo;
    private Long userId;
    private String userName; //user 테이블 컬럼 매핑을 위해 추가
    private String password;
    @Setter
    private LocalDateTime createdAt; //Setter를 통해 now()값 주입
    @Setter
    private LocalDateTime updatedAt; //Setter를 통해 now()값 주입
    private boolean deleted; //삭제 여부

    public Schedule(String todo, Long userId, String password) {
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }

    public Schedule(Long scheduleId, String todo, Long userId, String userName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.todo = todo;
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
