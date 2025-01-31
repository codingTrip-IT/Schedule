package com.example.schedule.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @Size(max = 200)
    @NotEmpty
    private String todo;
    private Long writerId;
    @NotEmpty
    private String password;

}
