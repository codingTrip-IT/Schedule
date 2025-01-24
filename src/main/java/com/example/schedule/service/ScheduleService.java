package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules(LocalDateTime updatedAt, String writer);

    ScheduleResponseDto findScheduleById(Long id);
}
