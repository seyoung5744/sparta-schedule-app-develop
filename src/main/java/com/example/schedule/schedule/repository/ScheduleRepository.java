package com.example.schedule.schedule.repository;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.exception.InvalidScheduleException;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.schedule.schedule.exception.ScheduleErrorCode.INVALID_SCHEDULE;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsById(Long id);

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new InvalidScheduleException(INVALID_SCHEDULE));
    }
}
