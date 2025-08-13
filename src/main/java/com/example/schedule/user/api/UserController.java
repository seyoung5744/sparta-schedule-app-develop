package com.example.schedule.user.api;

import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.common.response.ApiResponse;
import com.example.schedule.user.api.docs.UserApi;
import com.example.schedule.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.user.dto.response.UserInfoResponse;
import com.example.schedule.user.service.UserService;
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
public class UserController implements UserApi {

    private final UserService userService;

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
        HttpSession session = httpRequest.getSession();
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");

        UserInfoResponse userInfoResponse = userService.updateUserInfo(id, request, authInfoResponse.getId());
        return ApiResponse.success(userInfoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute("login_user");

        userService.deleteUserById(id, authInfoResponse.getId());
        return ApiResponse.noContent();
    }
}
