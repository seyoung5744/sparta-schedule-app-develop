package com.example.schedule.auth.dto.request;

import com.example.schedule.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    @NotBlank(message = "회원 이름은 필수입니다.")
    @Size(min = 2, message = "회원 이름은 최소 2글자 이상입니다.")
    private final String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-]).+$",
            message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    private final String password;

    public User toEntity() {
        return User.create(name, email, password);
    }
}
