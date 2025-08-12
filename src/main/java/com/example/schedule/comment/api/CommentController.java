package com.example.schedule.comment.api;

import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.comment.dto.request.CreateCommentRequest;
import com.example.schedule.comment.dto.response.CommentListResponse;
import com.example.schedule.comment.dto.response.CommentResponse;
import com.example.schedule.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "일정에 댓글을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "댓글 생성 성공")
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long scheduleId, @Valid @RequestBody CreateCommentRequest request, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");
        Long loginId = authInfoResponse.getId();

        CommentResponse commentResponse = commentService.addComment(scheduleId, loginId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponse);
    }

    @Operation(summary = "댓글 목록 조회", description = "댓글 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공")
    @GetMapping
    public ResponseEntity<CommentListResponse> getAllComments(@PathVariable Long scheduleId) {
        CommentListResponse commentListResponse = commentService.getAllComments(scheduleId);
        return ResponseEntity.ok(commentListResponse);
    }

    @Operation(summary = "댓글 단건 조회", description = "댓글 단건을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 단건 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long scheduleId, @PathVariable Long id) {
        CommentResponse commentResponse = commentService.getComment(scheduleId, id);
        return ResponseEntity.ok(commentResponse);
    }
}
