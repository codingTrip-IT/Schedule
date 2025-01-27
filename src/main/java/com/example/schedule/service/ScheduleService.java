package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId);

    ScheduleResponseDto findScheduleById(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password);

    void deleteSchedule(Long scheduleId, String password);
}
