package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String todo;
    private String writer;
    private String password;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Schedule(LocalDateTime updatedAt, String writer) {
        this.updatedAt = updatedAt;
        this.writer = writer;
    }

    public Schedule(String todo, String writer, String password) {
        this.todo = todo;
        this.writer = writer;
        this.password = password;
    }

    public Schedule(Long id, String todo, String writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.todo = todo;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
