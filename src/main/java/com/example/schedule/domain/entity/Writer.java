package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Writer {

    private Long writerId;
    private String name;
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
