package com.example.schedule.user.service;

import com.example.schedule.auth.exception.ForbiddenUserAccessException;
import com.example.schedule.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.user.dto.response.UserInfoResponse;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.exception.DuplicationEmailException;
import com.example.schedule.user.exception.InvalidUserException;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.schedule.auth.exception.AuthErrorCode.FORBIDDEN_USER_ACCESS;
import static com.example.schedule.user.exception.UserErrorCode.DUPLICATE_EMAIL;
import static com.example.schedule.user.exception.UserErrorCode.INVALID_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserInfoResponse::of)
                .toList();
    }

    public UserInfoResponse getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));
        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long id, UpdateUserInfoRequest request, Long loginId) {

        if (isUserExistsByEmailAndIdIsNot(request.getEmail(), id)) {
            throw new DuplicationEmailException(DUPLICATE_EMAIL);
        }

        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));

        if (!loginUser.isOwnerOf(user)) {
            throw new ForbiddenUserAccessException(FORBIDDEN_USER_ACCESS);
        }

        user.updateNameAndEmail(request.getName(), request.getEmail());
        userRepository.flush();
        return UserInfoResponse.of(user);
    }

    @Transactional
    public void deleteUserById(Long id, Long loginId) {
        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));

        if (!loginUser.isOwnerOf(user)) {
            throw new ForbiddenUserAccessException(FORBIDDEN_USER_ACCESS);
        }

        userRepository.delete(user);
    }

    private boolean isUserExistsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByEmailAndIdIsNot(email, id);
    }

}
