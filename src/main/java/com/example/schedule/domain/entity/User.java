package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private Long userId;
    private String userName;
    private String email;
    @Setter
    private LocalDateTime createdAt;  //Setter를 통해 now()값 주입
    @Setter
    private LocalDateTime updatedAt;  //Setter를 통해 now()값 주입
    private boolean deleted; //삭제 여부

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public User(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
