package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto){

        // ServiceLayer 호출 및 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
//        return ResponseEntity.ok(scheduleService.saveSchedule(dto));
    }

//    @GetMapping
//    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
//            LocalDateTime updatedAt,
//            String writer
//    ) {
//        log.info("updatedAt={}", updatedAt);
//        log.info("writer={}", writer);
//
//        return ResponseEntity.ok(scheduleService.findAllSchedules(updatedAt,writer));
//    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("scheduleId") Long id){
//        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long id, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getTodo(), dto.getWriter(), dto.getPassword()),HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("scheduleId") Long id, @RequestBody ScheduleRequestDto dto){

        scheduleService.deleteSchedule(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
