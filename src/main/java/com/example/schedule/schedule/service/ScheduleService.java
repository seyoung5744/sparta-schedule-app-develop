package com.example.schedule.schedule.service;

import com.example.schedule.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.schedule.dto.response.ScheduleResponse;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.exception.InvalidScheduleException;
import com.example.schedule.schedule.exception.UnauthorizedScheduleAccessException;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.exception.InvalidUserException;
import com.example.schedule.user.exception.UserErrorCode;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.schedule.schedule.exception.ScheduleErrorCode.INVALID_SCHEDULE;
import static com.example.schedule.schedule.exception.ScheduleErrorCode.UNAUTHORIZED_SCHEDULE_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse create(final CreateScheduleRequest request, final Long loginId) {
        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));
        final Schedule schedule = scheduleRepository.save(request.toEntity(user));
        return ScheduleResponse.of(schedule);
    }

    public ScheduleResponse getScheduleById(final Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new InvalidScheduleException(INVALID_SCHEDULE));
        return ScheduleResponse.of(schedule);
    }

    public List<ScheduleResponse> getAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::of)
                .toList();
    }

    @Transactional
    public ScheduleResponse editScheduleTitleAndContents(final Long id, final EditScheduleTitleAndContentsRequest request, final Long loginId) {
        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new InvalidScheduleException(INVALID_SCHEDULE));

        if (!loginUser.isOwnerOf(schedule.getUser())) {
            throw new UnauthorizedScheduleAccessException(UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        schedule.updateTitleAndContents(request.getTitle(), request.getContents());
        return ScheduleResponse.of(schedule);
    }

    @Transactional
    public void deleteById(final Long id, final Long loginId) {
        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new InvalidScheduleException(INVALID_SCHEDULE));

        if (!loginUser.isOwnerOf(schedule.getUser())) {
            throw new UnauthorizedScheduleAccessException(UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        scheduleRepository.delete(schedule);
    }

}
