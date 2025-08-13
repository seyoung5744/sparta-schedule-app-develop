package com.example.schedule.auth.api.docs;

import com.example.schedule.auth.dto.request.LoginRequest;
import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Auth API", description = "인증 API")
public interface AuthApi {

    @Operation(
            summary = "회원가입",
            description = "회원 가입 절차에 따라 유저를 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공")
            }
    )
    @PostMapping("/sign-up")
    ResponseEntity<com.example.schedule.common.response.ApiResponse<AuthInfoResponse>> signUp(SignUpRequest request);

    @Operation(
            summary = "로그인",
            description = "로그인을 시도합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공")
            }
    )
    @PostMapping("/login")
    ResponseEntity<com.example.schedule.common.response.ApiResponse<AuthInfoResponse>> login(LoginRequest loginRequest, HttpServletRequest request);
}
