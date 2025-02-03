package com.example.schedule.presentation.controller;

import com.example.schedule.presentation.dto.ScheduleRequestDto;
import com.example.schedule.presentation.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "409", description = "schedule 정보 저장 실패") })
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto);

    @Operation(summary = "전체 schedule 정보 반환", description = "전체 schedule 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 전체 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 전체 정보 반환 실패") })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            LocalDate updatedAt,
            Long writerId
    );

    @Operation(summary = "전체 schedule 페이징 정보 반환", description = "전체 schedule 페이징 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 전체 페이징 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 전체 페이징 정보 반환 실패") })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulePaging(
            @RequestParam(value="pageNo", defaultValue="1") int pageNo,
            @RequestParam(value="pageSize", defaultValue="10") int pageSize);

    @Operation(summary = "schedule 단건 정보 반환", description = "schedule 단건 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 단건 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 단건 정보 반환 실패") })
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("scheduleId") Long scheduleId);

    @Operation(summary = "수정 schedule 정보 반환", description = "수정 schedule 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 수정 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 수정 정보 반환 실패") })
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto);

    @Operation(summary = "삭제 schedule 정보 반환", description = "삭제 schedule 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schedule 삭제 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "schedule 삭제 정보 반환 실패") })
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto);
}