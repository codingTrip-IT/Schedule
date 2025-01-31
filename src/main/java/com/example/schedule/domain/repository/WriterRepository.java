package com.example.schedule.domain.repository;

import com.example.schedule.presentation.dto.WriterResponseDto;
import com.example.schedule.domain.entity.Writer;

import java.util.List;

public interface WriterRepository {

    WriterResponseDto saveWriter(Writer writer);

    List<WriterResponseDto> findAllWriters();

    Writer findWriterByIdOrElseThrow(Long writerId);

    int updateWriter(Long writerId, String name, String email);

    int deleteWriter(Long writerId);
}
