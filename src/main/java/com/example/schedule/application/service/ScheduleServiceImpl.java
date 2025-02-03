package com.example.schedule.application.service;

import com.example.schedule.presentation.Exception.ApiError;
import com.example.schedule.presentation.Exception.ApplicationException;
import com.example.schedule.presentation.Exception.ErrorMessageCode;
import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
import com.example.schedule.domain.repository.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTodo(), dto.getUserId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getSchedules(LocalDate updatedAt, Long userId) {
        return scheduleRepository.findAllSchedules(updatedAt, userId);
    }

    @Override
    public List<ScheduleResponseDto> getSchedulesPaging(int pageNo, int pageSize) {
        return scheduleRepository.findAllSchedulePaging(pageNo, pageSize);
    }

    @Override
    public ScheduleResponseDto getSchedule(Long scheduleId) {

        try{
            boolean deleted = scheduleRepository.validateDeleted(scheduleId);

            if (deleted){
                throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError("deleted", "이미 삭제된 정보입니다. 다시 입력하세요")));
            }

            Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

            return new ScheduleResponseDto(schedule);
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "잘못된 정보입니다. 다시 입력하세요")));
        }
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password) {

        System.out.println(todo);
        System.out.println(password);

        if (!StringUtils.hasText(todo) || !StringUtils.hasText(password)) {
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("required values", "할 일과 비밀번호는 필수값입니다.")));
        }

        String dbPassword = scheduleRepository.validatePassword(scheduleId);

        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        int updatedRow = scheduleRepository.updateSchedule(scheduleId, todo, password);

        if (updatedRow == 0) {
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+scheduleId)));
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        if (password.isEmpty()) {
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("required values", "비밀번호는 필수값입니다.")));
        }

        String dbPassword = scheduleRepository.validatePassword(scheduleId);

        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        int deletedRow = scheduleRepository.deleteSchedule(scheduleId, password);

        if (deletedRow == 0) {
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+scheduleId)));
        }
    }
}
