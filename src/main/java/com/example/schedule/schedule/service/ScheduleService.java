package com.example.schedule.schedule.service;

import com.example.schedule.schedule.dto.request.CreateScheduleRequest;
import com.example.schedule.schedule.dto.request.EditScheduleTitleAndContentsRequest;
import com.example.schedule.schedule.dto.response.PagingScheduleResponse;
import com.example.schedule.schedule.dto.response.ScheduleResponse;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.exception.UnauthorizedScheduleAccessException;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.schedule.schedule.exception.ScheduleErrorCode.UNAUTHORIZED_SCHEDULE_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse create(final CreateScheduleRequest request, final Long loginId) {
        User user = userRepository.findByIdOrElseThrow(loginId);
        final Schedule schedule = scheduleRepository.save(request.toEntity(user));
        return ScheduleResponse.of(schedule);
    }

    public ScheduleResponse getScheduleById(final Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return ScheduleResponse.of(schedule);
    }

    public PagingScheduleResponse getAllSchedule(Pageable pageable) {
        return PagingScheduleResponse.of(
                scheduleRepository.findAll(pageable)
        );
    }

    @Transactional
    public ScheduleResponse editScheduleTitleAndContents(final Long id, final EditScheduleTitleAndContentsRequest request, final Long loginId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!loginUser.isOwnerOf(schedule.getUser())) {
            throw new UnauthorizedScheduleAccessException(UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        schedule.updateTitleAndContents(request.getTitle(), request.getContents());
        return ScheduleResponse.of(schedule);
    }

    @Transactional
    public void deleteById(final Long id, final Long loginId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!loginUser.isOwnerOf(schedule.getUser())) {
            throw new UnauthorizedScheduleAccessException(UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        scheduleRepository.delete(schedule);
    }

}
