package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.request.CreateCommentRequest;
import com.example.schedule.comment.dto.request.EditCommentRequest;
import com.example.schedule.comment.dto.response.CommentListResponse;
import com.example.schedule.comment.dto.response.CommentResponse;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.exception.CommentErrorCode;
import com.example.schedule.comment.exception.InvalidCommentException;
import com.example.schedule.comment.exception.UnauthorizedCommentAccessException;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.exception.InvalidScheduleException;
import com.example.schedule.schedule.exception.ScheduleErrorCode;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.exception.InvalidUserException;
import com.example.schedule.user.exception.UserErrorCode;
import com.example.schedule.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.schedule.comment.exception.CommentErrorCode.UNAUTHORIZED_COMMENT_ACCESS;

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

        Comment comment = commentRepository.save(request.toEntity(user, schedule));
        return CommentResponse.of(comment, schedule);
    }

    public CommentListResponse getAllComments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE));
        List<Comment> comments = commentRepository.findAllBySchedule(schedule);
        return CommentListResponse.of(schedule, comments);
    }

    public CommentResponse getComment(Long scheduleId, Long id) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE));

        Comment comment = commentRepository.findByIdAndSchedule(id, schedule)
                .orElseThrow(() -> new InvalidCommentException(CommentErrorCode.INVALID_COMMENT));

        return CommentResponse.of(comment, schedule);
    }

    /**
     * 댓글 작성자와 로그인 유저가 동일하다면 수정 가능.
     */
    @Transactional
    public CommentResponse editComment(Long loginId, Long scheduleId, Long id, @Valid EditCommentRequest request) {
        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new InvalidCommentException(CommentErrorCode.INVALID_COMMENT));

        if (!user.isOwnerOf(comment.getUser())) {
            throw new UnauthorizedCommentAccessException(UNAUTHORIZED_COMMENT_ACCESS);
        }

        comment.updateContents(request.getContents());
        commentRepository.flush();
        return CommentResponse.of(comment, schedule);
    }

    @Transactional
    public void deleteComment(Long loginId, Long scheduleId, Long id) {

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new InvalidScheduleException(ScheduleErrorCode.INVALID_SCHEDULE);
        }

        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(UserErrorCode.INVALID_USER));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new InvalidCommentException(CommentErrorCode.INVALID_COMMENT));

        if (!user.isOwnerOf(comment.getUser())) {
            throw new UnauthorizedCommentAccessException(UNAUTHORIZED_COMMENT_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
