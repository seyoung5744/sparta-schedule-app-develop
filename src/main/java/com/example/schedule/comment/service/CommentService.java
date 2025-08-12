package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.request.CreateCommentRequest;
import com.example.schedule.comment.dto.response.CommentListResponse;
import com.example.schedule.comment.dto.response.CommentResponse;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.exception.InvalidScheduleException;
import com.example.schedule.schedule.exception.ScheduleErrorCode;
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

import static com.example.schedule.schedule.exception.ScheduleErrorCode.UNAUTHORIZED_SCHEDULE_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse addComment(Long scheduleId, Long loginId, CreateCommentRequest request) {

        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE));

        // 일정을 등록한 유저와 로그인 유저가 동일하지 않으면 "일정에 접근 권한이 없습니다."
        if (!user.isOwnerOf(schedule.getUser())) {
            throw new UnauthorizedScheduleAccessException(UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        Comment comment = commentRepository.save(request.toEntity(user, schedule));
        return CommentResponse.of(comment, user, schedule);
    }

    public CommentListResponse getAllComments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE));
        List<Comment> comments = commentRepository.findAllBySchedule(schedule);
        return CommentListResponse.of(schedule.getUser(), schedule, comments);
    }
}
