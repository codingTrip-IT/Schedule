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
    private String userName;
    private String password;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    private boolean deleted;

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

    public Schedule(Long scheduleId, String todo, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted) {
        this.scheduleId = scheduleId;
        this.todo = todo;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }
}
