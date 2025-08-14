package com.example.schedule.domain.comment.service;

import com.example.schedule.domain.comment.dto.request.CreateCommentRequest;
import com.example.schedule.domain.comment.dto.request.EditCommentRequest;
import com.example.schedule.domain.comment.dto.response.CommentListResponse;
import com.example.schedule.domain.comment.dto.response.CommentResponse;
import com.example.schedule.domain.comment.entity.Comment;
import com.example.schedule.domain.comment.exception.InvalidCommentException;
import com.example.schedule.domain.comment.exception.UnauthorizedCommentAccessException;
import com.example.schedule.domain.comment.repository.CommentRepository;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.schedule.exception.InvalidScheduleException;
import com.example.schedule.domain.schedule.repository.ScheduleRepository;
import com.example.schedule.domain.user.entity.User;
import com.example.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.schedule.domain.comment.exception.CommentErrorCode.INVALID_COMMENT;
import static com.example.schedule.domain.comment.exception.CommentErrorCode.UNAUTHORIZED_COMMENT_ACCESS;
import static com.example.schedule.domain.schedule.exception.ScheduleErrorCode.INVALID_SCHEDULE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse addComment(Long scheduleId, Long loginId, CreateCommentRequest request) {
        User user = userRepository.findByIdOrElseThrow(loginId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment comment = commentRepository.save(request.toEntity(user, schedule));
        return CommentResponse.of(comment, schedule);
    }

    public CommentListResponse getAllComments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        List<Comment> comments = commentRepository.findAllBySchedule(schedule);
        return CommentListResponse.of(schedule, comments);
    }

    public CommentResponse getComment(Long scheduleId, Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment comment = commentRepository.findByIdAndSchedule(id, schedule)
                .orElseThrow(() -> new InvalidCommentException(INVALID_COMMENT));

        return CommentResponse.of(comment, schedule);
    }

    /**
     * 댓글 작성자와 로그인 유저가 동일하다면 수정 가능.
     */
    @Transactional
    public CommentResponse editComment(Long loginId, Long scheduleId, Long id, EditCommentRequest request) {
        User user = userRepository.findByIdOrElseThrow(loginId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        if (!user.isOwnerOf(comment.getUser())) {
            throw new UnauthorizedCommentAccessException(UNAUTHORIZED_COMMENT_ACCESS);
        }

        comment.updateContents(request.getContents());
        commentRepository.flush();
        return CommentResponse.of(comment, schedule);
    }

    @Transactional
    public void deleteComment(Long loginId, Long scheduleId, Long id) throws InvalidScheduleException {

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new InvalidScheduleException(INVALID_SCHEDULE);
        }

        User user = userRepository.findByIdOrElseThrow(loginId);
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        if (!user.isOwnerOf(comment.getUser())) {
            throw new UnauthorizedCommentAccessException(UNAUTHORIZED_COMMENT_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
