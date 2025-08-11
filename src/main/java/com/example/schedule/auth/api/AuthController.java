package com.example.schedule.auth.api;

import com.example.schedule.auth.dto.request.LoginRequest;
import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원 가입 절차에 따라 유저를 생성합니다")
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    @PostMapping("/sign-up")
    public ResponseEntity<AuthInfoResponse> signUp(@RequestBody SignUpRequest request) {
        AuthInfoResponse authInfoResponse = authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authInfoResponse);
    }

    @Operation(summary = "로그인", description = "로그인을 시도합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<AuthInfoResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        AuthInfoResponse authInfoResponse = authService.login(loginRequest);

        HttpSession session = request.getSession();
        session.setAttribute("login_user", authInfoResponse);

        return ResponseEntity.ok(authInfoResponse);
    }
}
