package com.example.schedule.auth.service;

import com.example.schedule.auth.dto.request.LoginRequest;
import com.example.schedule.auth.dto.request.SignUpRequest;
import com.example.schedule.auth.dto.response.AuthInfoResponse;
import com.example.schedule.auth.exception.AuthErrorCode;
import com.example.schedule.auth.exception.InvalidCredentialsException;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.exception.DuplicationEmailException;
import com.example.schedule.user.exception.InvalidUserException;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.example.schedule.user.exception.UserErrorCode.DUPLICATE_EMAIL;
import static com.example.schedule.user.exception.UserErrorCode.INVALID_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public AuthInfoResponse signUp(SignUpRequest request) {

        if (isUserExistsByEmail(request.getEmail())) {
            throw new DuplicationEmailException(DUPLICATE_EMAIL);
        }

        User user = userRepository.save(request.toEntity());
        return AuthInfoResponse.of(user);
    }

    public AuthInfoResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));

        validatePasswordMatch(user.getPassword(), request.getPassword());
        return AuthInfoResponse.of(user);
    }

    private boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validatePasswordMatch(String storedPassword, String inputPassword) {
        if (!ObjectUtils.nullSafeEquals(storedPassword, inputPassword)) {
            throw new InvalidCredentialsException(AuthErrorCode.INVALID_CREDENTIALS);
        }
    }
}
