package com.example.schedule.domain.schedule.api;

import com.example.schedule.common.response.ApiResponse;
import com.example.schedule.common.session.SessionService;
import com.example.schedule.domain.schedule.api.docs.ScheduleApi;
import com.example.schedule.domain.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.domain.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.domain.schedule.dto.response.PagingScheduleResponse;
import com.example.schedule.domain.schedule.dto.response.ScheduleResponse;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final ScheduleService scheduleService;
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> create(@Valid @RequestBody CreateScheduleRequest request, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        ScheduleResponse scheduleResponse = scheduleService.create(request, loginUserId);
        return ApiResponse.created(scheduleResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> getSchedule(@PathVariable Long id) {
        ScheduleResponse scheduleResponse = scheduleService.getScheduleById(id);
        return ApiResponse.success(scheduleResponse);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<PagingScheduleResponse>> getAllSchedule(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.sort(Schedule.class)
                .by(Schedule::getModifiedAt)
                .descending());

        return ApiResponse.success(scheduleService.getAllSchedule(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> editScheduleTitleAndContents(@PathVariable Long id, @Valid @RequestBody EditScheduleTitleAndContentsRequest request, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        ScheduleResponse scheduleResponse = scheduleService.editScheduleTitleAndContents(id, request, loginUserId);
        return ApiResponse.success(scheduleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        scheduleService.deleteById(id, loginUserId);
        return ApiResponse.noContent();
    }
}
