package com.example.schedule.schedule.api;

import com.example.schedule.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.schedule.dto.response.ScheduleResponse;
import com.example.schedule.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "일정 생성", description = "새로운 일정을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "일정 생성 성공")
    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody CreateScheduleRequest request) {
        ScheduleResponse scheduleResponse = scheduleService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleResponse);
    }

    @Operation(summary = "특정 일정 조회", description = "ID에 해당하는 일정을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "일정 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long id) {
        ScheduleResponse scheduleResponse = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(scheduleResponse);
    }


    @Operation(summary = "전체 일정 조회", description = "전체 일정을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "전체 일정 조회 성공")
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getAllSchedule() {
        List<ScheduleResponse> scheduleResponses = scheduleService.getAllSchedule();
        return ResponseEntity.ok(scheduleResponses);
    }

    @Operation(summary = "일정 수정", description = "ID에 해당하는 일정의 제목, 내용을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "일정 수정 성공")
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> editScheduleTitleAndContents(@PathVariable Long id, @RequestBody EditScheduleTitleAndContentsRequest request) {
        ScheduleResponse scheduleResponse = scheduleService.editScheduleTitleAndContents(id, request);
        return ResponseEntity.ok(scheduleResponse);
    }
}
