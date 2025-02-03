package com.example.schedule.application.service;

import com.example.schedule.domain.repository.JdbcTempleteScheduleRepositoryImpl;
import com.example.schedule.presentation.Exception.ApiError;
import com.example.schedule.presentation.Exception.ApplicationException;
import com.example.schedule.presentation.Exception.ErrorMessageCode;
import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
import com.example.schedule.domain.repository.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청받은 데이터로 schedule 객체 생성 ID 없음
        Schedule schedule = new Schedule(dto.getTodo(), dto.getWriterId(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId) {
        return scheduleRepository.findAllSchedules(updatedAt, writerId);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedulePaging(int pageNo, int pageSize) {
        return scheduleRepository.findAllSchedulePaging(pageNo, pageSize);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {

            Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
            if (schedule == null){
                throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError("id", "잘못된 정보입니다. 다시 입력하세요")));
            }

            boolean deleted = scheduleRepository.validateDeleted(scheduleId);

            if (deleted){
                throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError("deleted", "이미 삭제된 정보입니다. 다시 입력하세요")));
            }

            return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password) {

        if (todo == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The todo, writer and password are required values.");
        }

        String dbPassword = scheduleRepository.validatePassword(scheduleId);

        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        int updatedRow = scheduleRepository.updateSchedule(scheduleId, todo, password);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password are required values.");
        }

        String dbPassword = scheduleRepository.validatePassword(scheduleId);

        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        int deletedRow = scheduleRepository.deleteSchedule(scheduleId, password);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId);
        }
    }
}
