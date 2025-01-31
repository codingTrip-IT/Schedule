package com.example.schedule.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class WriterRequestDto {

    private String name;
    @Email
    private String email;

}
