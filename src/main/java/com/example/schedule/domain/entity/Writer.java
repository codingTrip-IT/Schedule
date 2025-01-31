package com.example.schedule.domain.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Writer {

    private Long writerId;
    private String name;
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotNull
    private String email;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Writer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
