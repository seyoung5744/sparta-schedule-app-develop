package com.example.schedule.user.service;

import com.example.schedule.user.dto.request.UpdateUserInfoRequest;
import com.example.schedule.user.dto.response.UserInfoResponse;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long id, UpdateUserInfoRequest request) {

        if (isUserExistsByEmailAndIdIsNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        user.updateNameAndEmail(request.getName(), request.getEmail());
        userRepository.flush();
        return UserInfoResponse.of(user);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
        userRepository.delete(user);
    }

    private boolean isUserExistsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByEmailAndIdIsNot(email, id);
    }
}
