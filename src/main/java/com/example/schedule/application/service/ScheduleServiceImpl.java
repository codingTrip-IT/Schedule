package com.example.schedule.application.service;

import com.example.schedule.exception.ApiError;
import com.example.schedule.exception.ApplicationException;
import com.example.schedule.exception.ErrorMessageCode;
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

    /* 생성자 주입 */
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /* 일정 생성 */
    @Transactional
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTodo(), dto.getUserId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    /* 일정 전체 조회 */
    @Override
    public List<ScheduleResponseDto> getSchedules(LocalDate updatedAt, Long userId) {
        return scheduleRepository.findAllSchedules(updatedAt, userId);
    }

    /* 일정 전체 조회 - 페이징 */
    @Override
    public List<ScheduleResponseDto> getSchedulesPaging(int pageNo, int pageSize) {
        return scheduleRepository.findAllSchedulePaging(pageNo, pageSize);
    }

    /* 일정 선택 조회 */
    @Override
    public ScheduleResponseDto getSchedule(Long scheduleId) {

        try{
            // id에 해당하는 값의 deleted 컬럼 값 조회(기본값 false)
            boolean deleted = scheduleRepository.validateDeleted(scheduleId);

            // 삭제된 일정인지 확인 (deleted = true이면 예외 발생)
            if (deleted){
                throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError("deleted", "이미 삭제된 정보입니다. 다시 입력하세요")));
            }

            // 선택한 일정 반환
            Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);
            return new ScheduleResponseDto(schedule);
        } catch (EmptyResultDataAccessException e) {
            // 테이블에 없는 id 값인 경우 예외처리
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "잘못된 정보입니다. 다시 입력하세요")));
        }
    }

    /* 일정 선택 수정 */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String todo, String password) {

        // 필수 값 검증 (to do(일정)이 없거나, password(비밀번호)가 없을 경우 예외처리)
        if (!StringUtils.hasText(todo) || !StringUtils.hasText(password)) {
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("required values", "할 일과 비밀번호는 필수값입니다.")));
        }

        // 입력된 비밀번호가 DB의 비밀번호와 일치하는지 확인(불일치 시 예외처리)
        String dbPassword = scheduleRepository.validatePassword(scheduleId);
        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        //  일정 수정
        int updatedRow = scheduleRepository.updateSchedule(scheduleId, todo, password);
        if (updatedRow == 0) {  // 존재하지 않는 일정이면 예외 발생
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+scheduleId)));
        }

        // 수정된 일정 반환
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    /* 일정 선택 삭제 (Soft Delete) */
    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        // 비밀번호 필수 값 검증
        if (password.isEmpty()) {
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("required values", "비밀번호는 필수값입니다.")));
        }

        // 입력된 비밀번호가 DB의 비밀번호와 일치하는지 확인
        String dbPassword = scheduleRepository.validatePassword(scheduleId);
        if (!dbPassword.equals(password)){
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("password", "비밀번호가 일치하지 않습니다.")));
        }

        // 일정 삭제 (Soft Delete)
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId, password);
        if (deletedRow == 0) { // 존재하지 않는 일정이면 예외 발생
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+scheduleId)));
        }
    }
}
