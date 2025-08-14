package com.example.schedule.domain.auth.service;

import com.example.schedule.domain.auth.dto.request.LoginRequest;
import com.example.schedule.domain.auth.dto.request.SignUpRequest;
import com.example.schedule.domain.auth.dto.response.AuthInfoResponse;
import com.example.schedule.domain.auth.exception.InvalidCredentialsException;
import com.example.schedule.common.utils.PasswordEncoder;
import com.example.schedule.domain.user.entity.User;
import com.example.schedule.domain.user.exception.DuplicationEmailException;
import com.example.schedule.domain.user.exception.InvalidEmailException;
import com.example.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.schedule.domain.auth.exception.AuthErrorCode.INVALID_CREDENTIALS;
import static com.example.schedule.domain.user.exception.UserErrorCode.DUPLICATE_EMAIL;
import static com.example.schedule.domain.user.exception.UserErrorCode.INVALID_EMAIL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthInfoResponse signUp(SignUpRequest request) {

        if (isUserExistsByEmail(request.getEmail())) {
            throw new DuplicationEmailException(DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userRepository.save(request.toEntity(encodedPassword));
        return AuthInfoResponse.of(user);
    }

    public AuthInfoResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidEmailException(INVALID_EMAIL));

        validatePasswordMatch(request.getPassword(), user.getPassword());
        return AuthInfoResponse.of(user);
    }

    private boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validatePasswordMatch(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS);
        }
    }
}
