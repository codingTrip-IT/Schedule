package com.example.schedule.application.service;

import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getSchedules(LocalDate updatedAt, Long writerId);

    List<ScheduleResponseDto> getSchedulesPaging(int pageNo, int pageSize);

    ScheduleResponseDto getSchedule(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password);

    void deleteSchedule(Long scheduleId, String password);

}
