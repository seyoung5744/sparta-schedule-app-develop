package com.example.schedule.domain.user.api;

import com.example.schedule.common.response.ApiResponse;
import com.example.schedule.common.session.SessionService;
import com.example.schedule.domain.user.api.docs.UserApi;
import com.example.schedule.domain.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.domain.user.dto.response.UserInfoResponse;
import com.example.schedule.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserInfoResponse>>> getAllUsers() {
        List<UserInfoResponse> userInfoResponses = userService.getAllUsers();
        return ApiResponse.success(userInfoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(@PathVariable Long id) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(id);
        return ApiResponse.success(userInfoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> editUserInfo(@PathVariable Long id, @Valid @RequestBody UpdateUserInfoRequest request, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        UserInfoResponse userInfoResponse = userService.updateUserInfo(id, request, loginUserId);
        return ApiResponse.success(userInfoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long loginUserId = sessionService.getLoginUserIdFromSession(httpRequest);
        userService.deleteUserById(id, loginUserId);
        return ApiResponse.noContent();
    }
}
