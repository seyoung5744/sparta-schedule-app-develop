package com.example.schedule.common.session;

import com.example.schedule.auth.dto.response.AuthInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    private static final String LOGIN_USER = "login_user";

    public void saveLoginUserToSession(HttpServletRequest request, AuthInfoResponse authInfoResponse) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, authInfoResponse);
    }

    public Long getLoginUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        AuthInfoResponse authInfoResponse = (AuthInfoResponse) session.getAttribute(LOGIN_USER);
        return authInfoResponse.getId();
    }

    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("login_user") != null;
    }
}
