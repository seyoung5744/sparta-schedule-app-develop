package com.example.schedule.auth.service;

import com.example.schedule.auth.dto.request.LoginRequest;
import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public AuthInfoResponse signUp(SignUpRequest request) {

        if (isUserExistsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        User user = userRepository.save(request.toEntity());
        return AuthInfoResponse.of(user);
    }

    public AuthInfoResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        validatePasswordMatch(user.getPassword(), request.getPassword());
        return AuthInfoResponse.of(user);
    }

    private boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validatePasswordMatch(String storedPassword, String inputPassword) {
        if (!ObjectUtils.nullSafeEquals(storedPassword, inputPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
