package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청받은 데이터로 schedule 객체 생성 ID 없음
        Schedule schedule = new Schedule(dto.getTodo(), dto.getWriter(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDateTime updatedAt, String writer) {

        Schedule schedule = new Schedule(updatedAt, writer);

        return scheduleRepository.findAllSchedules(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findMemoByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }
}
