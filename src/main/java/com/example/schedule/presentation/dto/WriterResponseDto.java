package com.example.schedule.presentation.dto;

import com.example.schedule.domain.entity.Writer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WriterResponseDto {

    private Long writerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WriterResponseDto(Writer writer) {
        this.writerId = writer.getWriterId();
        this.name = writer.getName();
        this.email = writer.getEmail();
        this.createdAt = writer.getCreatedAt();
        this.updatedAt = writer.getUpdatedAt();
    }

}
