package com.example.schedule.domain.auth.filter;

import com.example.schedule.common.session.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AuthCheckFilter implements Filter {

    private static final List<String> WHITE_LIST = List.of(
            "/",
            "/v1/auth/sign-up",
            "/v1/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**");

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("인증 체크 필터 시작 {}", requestURI);

        if (isRequiredAuthPath(requestURI)) {
            log.info("인증 체크 로직 실행 {}", requestURI);
            if (!sessionService.isAuthenticated(httpRequest)) {
                log.info("미인증 사용자 요청 {}", requestURI);
                writeUnauthorizedResponse(httpRequest, httpResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRequiredAuthPath(String requestURI) {
        return WHITE_LIST.stream().noneMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

    private void writeUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> responseBody = Map.of(
                "message", "로그인 해주세요",
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", HttpStatus.UNAUTHORIZED,
                "path", request.getRequestURI()
        );

        // JSON 변환 후 응답
        response.getWriter().print(objectMapper.writeValueAsString(responseBody));
    }
}
