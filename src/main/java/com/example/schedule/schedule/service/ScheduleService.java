package com.example.schedule.schedule.service;

import com.example.schedule.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.schedule.dto.response.ScheduleResponse;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse create(final CreateScheduleRequest request) {
        final Schedule schedule = scheduleRepository.save(request.toEntity());
        return ScheduleResponse.of(schedule);
    }

    public ScheduleResponse getScheduleById(final Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 일정입니다."));
        return ScheduleResponse.of(schedule);
    }

    public List<ScheduleResponse> getAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::of)
                .toList();
    }

    @Transactional
    public ScheduleResponse editScheduleTitleAndContents(final Long id, final EditScheduleTitleAndContentsRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 일정입니다."));

        schedule.updateTitleAndContents(request.getTitle(), request.getContents());
        return ScheduleResponse.of(schedule);
    }

    @Transactional
    public void deleteById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 일정입니다."));
        scheduleRepository.delete(schedule);
    }
}
