package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId);

    Schedule findScheduleByIdOrElseThrow(Long schedule_id);

    int updateSchedule(Long schedule_id, String todo, String password);

    int deleteSchedule(Long schedule_id, String password);
}
