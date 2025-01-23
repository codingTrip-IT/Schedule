package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String todo;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(String todo, String writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.todo = todo;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
