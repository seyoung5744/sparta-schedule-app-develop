package com.example.schedule.domain.schedule.api.docs;

import com.example.schedule.domain.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.domain.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.domain.schedule.dto.response.PagingScheduleResponse;
import com.example.schedule.domain.schedule.dto.response.ScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Schedule API", description = "일정 API")
public interface ScheduleApi {

    @PostMapping
    @Operation(
            summary = "일정 생성",
            description = "새로운 일정을 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "일정 생성 성공")

            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<ScheduleResponse>> create(CreateScheduleRequest request, HttpServletRequest httpRequest);

    @GetMapping("/{id}")
    @Operation(
            summary = "특정 일정 조회",
            description = "ID에 해당하는 일정을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "일정 조회 성공")

            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<ScheduleResponse>> getSchedule(Long id);


    @GetMapping
    @Operation(
            summary = "전체 일정 조회",
            description = "전체 일정을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 일정 조회 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<PagingScheduleResponse>> getAllSchedule(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PatchMapping("/{id}")
    @Operation(
            summary = "일정 수정",
            description = "ID에 해당하는 일정의 제목, 내용을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "일정 수정 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<ScheduleResponse>> editScheduleTitleAndContents(Long id, EditScheduleTitleAndContentsRequest request, HttpServletRequest httpRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "일정 삭제",
            description = "ID에 해당하는 일정을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "일정 삭제 성공 (No Content)")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<Void>> deleteSchedule(Long id, HttpServletRequest httpRequest);
}
