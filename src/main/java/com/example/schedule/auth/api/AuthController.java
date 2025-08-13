package com.example.schedule.auth.api;

import com.example.schedule.auth.api.docs.AuthApi;
import com.example.schedule.auth.dto.request.LoginRequest;
import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.auth.service.AuthService;
import com.example.schedule.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthInfoResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        AuthInfoResponse authInfoResponse = authService.signUp(request);
        return ApiResponse.created(authInfoResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthInfoResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        AuthInfoResponse authInfoResponse = authService.login(loginRequest);

        HttpSession session = request.getSession();
        session.setAttribute("login_user", authInfoResponse);

        return ApiResponse.success(authInfoResponse);
    }
}
