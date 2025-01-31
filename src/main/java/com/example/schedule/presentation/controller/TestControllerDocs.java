package com.example.schedule.presentation.controller;

import com.example.schedule.domain.entity.Schedule;
import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "schedule", description = "schedule 관련 API입니다.")
public interface TestControllerDocs {

    @Operation(summary = "schedule 정보 저장", description = "schedule 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 정보 저장 성공"),
            @ApiResponse(responseCode = "409", description = "schedule 정보 저장 실패(유저 중복)") })
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto);

    @Operation(summary = "전체 schedule 정보 반환", description = "전체 schedule 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 전체 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 전체 정보 반환 실패") })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            LocalDate updatedAt,
            Long writerId
    );

}