package com.example.schedule.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditScheduleTitleAndContentsRequest {

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "일정 제목은 2자 이상 100자 이하입니다.")
    private final String title;

    @NotBlank(message = "일정 내용은 필수입니다.")
    @Size(min = 5, max = 1000, message = "일정 내용은 5자 이상 1000자 이하입니다.")
    private final String contents;

}
