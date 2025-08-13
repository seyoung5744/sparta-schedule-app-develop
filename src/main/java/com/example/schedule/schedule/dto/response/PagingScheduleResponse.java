package com.example.schedule.schedule.dto.response;

import com.example.schedule.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagingScheduleResponse {

    private final int pageNumber;
    private final int pageSize;
    private final int pageElements;
    private final int totalPages;
    private final long totalElements;
    private final List<ScheduleResponse> schedules;

    @Builder
    private PagingScheduleResponse(int pageNumber, int pageSize, int pageElements, int totalPages, long totalElements, List<ScheduleResponse> schedules) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageElements = pageElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.schedules = schedules;
    }

    public static PagingScheduleResponse of(Page<Schedule> schedulePage) {
        List<ScheduleResponse> scheduleResponses = schedulePage.stream()
                .map(ScheduleResponse::of)
                .toList();

        return PagingScheduleResponse.builder()
                .pageNumber(schedulePage.getNumber())
                .pageSize(schedulePage.getSize())
                .pageElements(scheduleResponses.size())
                .totalPages(schedulePage.getTotalPages())
                .totalElements(schedulePage.getTotalElements())
                .schedules(scheduleResponses)
                .build();
    }
}
