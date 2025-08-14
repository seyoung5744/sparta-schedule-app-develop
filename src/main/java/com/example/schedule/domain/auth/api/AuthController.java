package com.example.schedule.domain.auth.api;

import com.example.schedule.domain.auth.api.docs.AuthApi;
import com.example.schedule.domain.auth.dto.request.LoginRequest;
import com.example.schedule.domain.auth.dto.request.SignUpRequest;
import com.example.schedule.domain.auth.dto.response.AuthInfoResponse;
import com.example.schedule.domain.auth.service.AuthService;
import com.example.schedule.common.response.ApiResponse;
import com.example.schedule.common.session.SessionService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final SessionService sessionService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthInfoResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        AuthInfoResponse authInfoResponse = authService.signUp(request);
        return ApiResponse.created(authInfoResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthInfoResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        AuthInfoResponse authInfoResponse = authService.login(loginRequest);
        sessionService.saveLoginUserToSession(request, authInfoResponse);
        return ApiResponse.success(authInfoResponse);
    }
}
