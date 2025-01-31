package com.example.schedule.application.service;

import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId);

    List<ScheduleResponseDto> findAllSchedulePaging(int pageNo, int pageSize);

    ScheduleResponseDto findScheduleById(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password);

    void deleteSchedule(Long scheduleId, String password);


}
