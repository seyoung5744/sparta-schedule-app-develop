package com.example.schedule.comment.api;

import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.comment.api.docs.CommentApi;
import com.example.schedule.comment.dto.request.CreateCommentRequest;
import com.example.schedule.comment.dto.request.EditCommentRequest;
import com.example.schedule.comment.dto.response.CommentListResponse;
import com.example.schedule.comment.dto.response.CommentResponse;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(@PathVariable Long scheduleId, @Valid @RequestBody CreateCommentRequest request, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");
        Long loginId = authInfoResponse.getId();

        CommentResponse commentResponse = commentService.addComment(scheduleId, loginId, request);
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
        HttpSession session = httpRequest.getSession(false);
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");
        Long loginId = authInfoResponse.getId();

        CommentResponse commentResponse = commentService.editComment(loginId, scheduleId, id, request);
        return ApiResponse.success(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long scheduleId, @PathVariable Long id, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");
        Long loginId = authInfoResponse.getId();

        commentService.deleteComment(loginId, scheduleId, id);
        return ApiResponse.noContent();
    }


}
