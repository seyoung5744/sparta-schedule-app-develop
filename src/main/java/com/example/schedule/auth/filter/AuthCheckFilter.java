package com.example.schedule.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthCheckFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/v1/auth/sign-up", "/v1/auth/login", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*"};

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("인증 체크 필터 시작 {}", requestURI);

        if (isRequiredAuthPath(requestURI)) {
            log.info("인증 체크 로직 실행 {}", requestURI);
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("login_user") == null) {
                log.info("미인증 사용자 요청 {}", requestURI);
                setResponse(httpRequest, httpResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRequiredAuthPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }


    private void setResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "로그인 해주세요");
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error", HttpStatus.UNAUTHORIZED);
        responseBody.put("path", request.getRequestURI());

        // JSON 변환 후 응답
        String json = objectMapper.writeValueAsString(responseBody);
        response.getWriter().print(json);
    }
}
