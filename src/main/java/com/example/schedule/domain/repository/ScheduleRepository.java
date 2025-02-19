package com.example.schedule.domain.repository;

import com.example.schedule.presentation.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId);

    List<ScheduleResponseDto> findAllSchedulePaging(int pageNo, int pageSize);

    Schedule findScheduleByIdOrElseThrow(Long scheduleId);

    boolean validateDeleted(Long userId);

    int updateSchedule(Long scheduleId, String todo, String password);

    String validatePassword(Long scheduleId);

    int deleteSchedule(Long scheduleId, String password);

}
