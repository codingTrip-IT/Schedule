package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, String writer);

    Schedule findMemoByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String todo, String writer, String password);

    int deleteSchedule(Long id, String password);
}
