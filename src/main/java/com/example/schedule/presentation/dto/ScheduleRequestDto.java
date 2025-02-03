package com.example.schedule.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @Size(max = 200)
    @NotEmpty
    private String todo; //할일은 최대 200자 이내로 제한, 필수값 처리
    private Long userId;
    @NotEmpty
    private String password; //비밀번호는 필수값 처리

}
