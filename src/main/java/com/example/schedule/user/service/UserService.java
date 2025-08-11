package com.example.schedule.user.service;

import com.example.schedule.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.user.dto.response.UserInfoResponse;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long id, UpdateUserInfoRequest request, Long loginId) {

        if (isUserExistsByEmailAndIdIsNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        if (!loginUser.isOwnerOf(user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        user.updateNameAndEmail(request.getName(), request.getEmail());
        userRepository.flush();
        return UserInfoResponse.of(user);
    }

    @Transactional
    public void deleteUserById(Long id, Long loginId) {
        User loginUser = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        if (!loginUser.isOwnerOf(user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        userRepository.delete(user);
    }

    private boolean isUserExistsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByEmailAndIdIsNot(email, id);
    }

}
