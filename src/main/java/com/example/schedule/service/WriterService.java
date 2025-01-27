package com.example.schedule.service;

import com.example.schedule.dto.WriterRequestDto;
import com.example.schedule.dto.WriterResponseDto;

import java.util.List;

public interface WriterService {

    WriterResponseDto saveWriter(WriterRequestDto dto);

    List<WriterResponseDto> findAllWriters();

    WriterResponseDto findWriterById(Long writerId);

    WriterResponseDto updateWriter(Long writerId, String name, String email);

    void deleteWriter(Long writerId);
}
