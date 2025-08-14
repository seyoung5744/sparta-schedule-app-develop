package com.example.schedule.domain.user.repository;

import com.example.schedule.domain.user.entity.User;
import com.example.schedule.domain.user.exception.InvalidUserException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.schedule.domain.user.exception.UserErrorCode.INVALID_USER;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, Long id);

    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new InvalidUserException(INVALID_USER));
    }
}
