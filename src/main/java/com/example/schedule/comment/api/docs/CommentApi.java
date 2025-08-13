package com.example.schedule.comment.api.docs;

import com.example.schedule.comment.dto.request.CreateCommentRequest;
import com.example.schedule.comment.dto.request.EditCommentRequest;
import com.example.schedule.comment.dto.response.CommentListResponse;
import com.example.schedule.comment.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Tag(name = "Comment API", description = "댓글 API")
public interface CommentApi {

    @PostMapping
    @Operation(
            summary = "댓글 생성",
            description = "일정에 댓글을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "댓글 생성 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<CommentResponse>> addComment(Long scheduleId, CreateCommentRequest request, HttpServletRequest httpRequest);

    @GetMapping
    @Operation(
            summary = "댓글 목록 조회",
            description = "댓글 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<CommentListResponse>> getAllComments(Long scheduleId);

    @GetMapping("/{id}")
    @Operation(
            summary = "댓글 단건 조회",
            description = "댓글 단건을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 단건 조회 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<CommentResponse>> getComment(Long scheduleId, Long id);

    @PatchMapping("/{id}")
    @Operation(
            summary = "댓글 수정",
            description = "댓글을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 수정 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<CommentResponse>> editComment(Long scheduleId, Long id, EditCommentRequest request, HttpServletRequest httpRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공 (No Content)")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<Void>> deleteComment(Long scheduleId, Long id, HttpServletRequest httpRequest);
}
