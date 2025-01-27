package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId);

    ScheduleResponseDto findScheduleById(Long schedule_id);

    ScheduleResponseDto updateSchedule(Long schedule_id, String todo, String password);

    void deleteSchedule(Long schedule_id, String password);
}
