package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private String todo;
    private String writer;
    private String password;

}
