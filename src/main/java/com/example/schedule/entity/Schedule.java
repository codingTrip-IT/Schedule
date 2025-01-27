package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String todo;
    private Long writerId;
    private String name;
    private String password;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Schedule(String todo, Long writerId, String password) {
        this.todo = todo;
        this.writerId = writerId;
        this.password = password;
    }

    public Schedule(Long scheduleId, String todo, Long writerId, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.todo = todo;
        this.writerId = writerId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
