package com.example.schedule.presentation.controller;

import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;
import com.example.schedule.application.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedules")
public class ScheduleController{

    private final ScheduleService scheduleService;

    /* 생성자 주입 */
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /* 일정 생성 */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    /* 일정 전체 조회 */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            LocalDate updatedAt,  //수정일
            Long userId           //사용자 id
    ) {
        log.info("updatedAt={}", updatedAt);
        log.info("userId={}", userId);

        return ResponseEntity.ok(scheduleService.getSchedules(updatedAt,userId));
    }

    /* 일정 전체 조회 - 페이징 */
    @GetMapping("/paging")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesPaging(
            @RequestParam(value="pageNo", defaultValue="1") int pageNo,         // pageNo:페이지 번호, 기본값 1
            @RequestParam(value="pageSize", defaultValue="5") int pageSize) {   //pageSize:페이지 크기, 기본값 5
        log.info("pageNo={}", pageNo);
        log.info("pageSize={}", pageSize);

        return ResponseEntity.ok(scheduleService.getSchedulesPaging(pageNo,pageSize));
    }

    /* 일정 선택 조회 */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleId") Long scheduleId){
        return ResponseEntity.ok(scheduleService.getSchedule(scheduleId));
    }

    /* 일정 선택 수정 */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto.getTodo(), dto.getPassword()),HttpStatus.OK);
    }

    /* 일정 선택 삭제 */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto){
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
