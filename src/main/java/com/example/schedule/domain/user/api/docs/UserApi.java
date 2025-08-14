package com.example.schedule.domain.user.api.docs;

import com.example.schedule.domain.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.domain.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Tag(name = "User API", description = "회원 API")
public interface UserApi {

    @GetMapping
    @Operation(
            summary = "회원 목록 조회",
            description = "회원 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<List<UserInfoResponse>>> getAllUsers();

    @GetMapping("/{id}")
    @Operation(
            summary = "회원 정보 조회",
            description = "회원 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<UserInfoResponse>> getUserInfo(Long id);

    @PatchMapping("/{id}")
    @Operation(
            summary = "회원 정보 수정",
            description = "회원 정보를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<UserInfoResponse>> editUserInfo(Long id, UpdateUserInfoRequest request, HttpServletRequest httpRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "회원 삭제",
            description = "회원을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원 삭제 성공 (No Content)")
            }
    )
    ResponseEntity<com.example.schedule.common.response.ApiResponse<Void>> deleteUser(Long id, HttpServletRequest httpRequest);
}
