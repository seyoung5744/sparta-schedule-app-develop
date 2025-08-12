package com.example.schedule.user.api;

import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.user.dto.response.UserInfoResponse;
import com.example.schedule.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<UserInfoResponse> userInfoResponses = userService.getAllUsers();
        return ResponseEntity.ok(userInfoResponses);
    }

    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long id) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(id);
        return ResponseEntity.ok(userInfoResponse);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공")
    @PatchMapping("/{id}")
    public ResponseEntity<UserInfoResponse> editUserInfo(@PathVariable Long id, @Valid @RequestBody UpdateUserInfoRequest request, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");

        UserInfoResponse userInfoResponse = userService.updateUserInfo(id, request, authInfoResponse.getId());
        return ResponseEntity.ok(userInfoResponse);
    }

    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "회원 삭제 성공 (No Content)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");

        userService.deleteUserById(id, authInfoResponse.getId());
        return ResponseEntity.noContent().build();
    }
}
