package com.example.schedule.domain.schedule.repository;

import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.schedule.exception.InvalidScheduleException;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.schedule.domain.schedule.exception.ScheduleErrorCode.INVALID_SCHEDULE;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsById(Long id);

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new InvalidScheduleException(INVALID_SCHEDULE));
    }
}
