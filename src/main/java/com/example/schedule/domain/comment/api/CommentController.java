package com.example.schedule.domain.comment.api;

import com.example.schedule.domain.comment.api.docs.CommentApi;
import com.example.schedule.domain.comment.dto.request.CreateCommentRequest;
import com.example.schedule.domain.comment.dto.request.EditCommentRequest;
import com.example.schedule.domain.comment.dto.response.CommentListResponse;
import com.example.schedule.domain.comment.dto.response.CommentResponse;
import com.example.schedule.domain.comment.service.CommentService;
import com.example.schedule.common.response.ApiResponse;
import com.example.schedule.common.session.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(@PathVariable Long scheduleId, @Valid @RequestBody CreateCommentRequest request, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        CommentResponse commentResponse = commentService.addComment(scheduleId, loginUserId, request);
        return ApiResponse.created(commentResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CommentListResponse>> getAllComments(@PathVariable Long scheduleId) {
        CommentListResponse commentListResponse = commentService.getAllComments(scheduleId);
        return ApiResponse.success(commentListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getComment(@PathVariable Long scheduleId, @PathVariable Long id) {
        CommentResponse commentResponse = commentService.getComment(scheduleId, id);
        return ApiResponse.success(commentResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> editComment(@PathVariable Long scheduleId, @PathVariable Long id, @Valid @RequestBody EditCommentRequest request, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        CommentResponse commentResponse = commentService.editComment(loginUserId, scheduleId, id, request);
        return ApiResponse.success(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long scheduleId, @PathVariable Long id, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        commentService.deleteComment(loginUserId, scheduleId, id);
        return ApiResponse.noContent();
    }


}
