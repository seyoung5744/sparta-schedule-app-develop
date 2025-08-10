package com.example.schedule.auth.service;

import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
