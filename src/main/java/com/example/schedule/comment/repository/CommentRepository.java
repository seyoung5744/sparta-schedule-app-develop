package com.example.schedule.comment.repository;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllBySchedule(Schedule schedule);

    Optional<Comment> findByIdAndSchedule(Long id, Schedule schedule);
}
