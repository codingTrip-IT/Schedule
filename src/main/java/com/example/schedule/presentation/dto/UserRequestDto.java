package com.example.schedule.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String userName;
    @Email
    private String email; //이메일 정보가 형식에 맞는지 확인

}
