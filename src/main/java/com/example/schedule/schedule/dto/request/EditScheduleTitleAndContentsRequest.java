package com.example.schedule.schedule.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditScheduleTitleAndContentsRequest {

    private final String title;
    private final String contents;

}
