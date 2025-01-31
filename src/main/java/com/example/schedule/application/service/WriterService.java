package com.example.schedule.application.service;

import com.example.schedule.presentation.dto.WriterRequestDto;
import com.example.schedule.presentation.dto.WriterResponseDto;

import java.util.List;

public interface WriterService {

    WriterResponseDto saveWriter(WriterRequestDto dto);

    List<WriterResponseDto> findAllWriters();

    WriterResponseDto findWriterById(Long writerId);

    WriterResponseDto updateWriter(Long writerId, String name, String email);

    void deleteWriter(Long writerId);
}
