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
public class ScheduleController implements TestControllerDocs{

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto){

        // ServiceLayer 호출 및 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
//        return ResponseEntity.ok(scheduleService.saveSchedule(dto));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            LocalDate updatedAt,
            Long writerId
    ) {
        log.info("updatedAt={}", updatedAt);
        log.info("writerId={}", writerId);

        return ResponseEntity.ok(scheduleService.findAllSchedules(updatedAt,writerId));
    }

    @GetMapping("/page")
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulePaging(
            @RequestParam(value="pageNo", defaultValue="1") int pageNo,
            @RequestParam(value="pageSize", defaultValue="10") int pageSize) {
        log.info("pageNo={}", pageNo);
        log.info("pageSize={}", pageSize);

        return ResponseEntity.ok(scheduleService.findAllSchedulePaging(pageNo,pageSize));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("scheduleId") Long scheduleId){
        return ResponseEntity.ok(scheduleService.findScheduleById(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto.getTodo(), dto.getPassword()),HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto){
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
