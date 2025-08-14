package com.example.schedule.domain.user.service;

import com.example.schedule.domain.auth.exception.ForbiddenUserAccessException;
import com.example.schedule.domain.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.domain.user.dto.response.UserInfoResponse;
import com.example.schedule.domain.user.entity.User;
import com.example.schedule.domain.user.exception.DuplicationEmailException;
import com.example.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.schedule.domain.auth.exception.AuthErrorCode.FORBIDDEN_USER_ACCESS;
import static com.example.schedule.domain.user.exception.UserErrorCode.DUPLICATE_EMAIL;

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
        User user = userRepository.findByIdOrElseThrow(id);
        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long id, UpdateUserInfoRequest request, Long loginId) {

        if (isUserExistsByEmailAndIdIsNot(request.getEmail(), id)) {
            throw new DuplicationEmailException(DUPLICATE_EMAIL);
        }

        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        User user = userRepository.findByIdOrElseThrow(id);

        if (!loginUser.isOwnerOf(user)) {
            throw new ForbiddenUserAccessException(FORBIDDEN_USER_ACCESS);
        }

        user.updateNameAndEmail(request.getName(), request.getEmail());
        userRepository.flush();
        return UserInfoResponse.of(user);
    }

    @Transactional
    public void deleteUserById(Long id, Long loginId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginId);
        User user = userRepository.findByIdOrElseThrow(id);

        if (!loginUser.isOwnerOf(user)) {
            throw new ForbiddenUserAccessException(FORBIDDEN_USER_ACCESS);
        }

        userRepository.delete(user);
    }

    private boolean isUserExistsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByEmailAndIdIsNot(email, id);
    }

}
