package com.example.schedule.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorMessageCode errorMessageCode;
    @Setter
    private List<ApiError> errors;

    // 추가적인 오류 목록을 포함하는 생성자
    public ApplicationException(ErrorMessageCode errorMessageCode, List<ApiError> errors) {
        super(errorMessageCode.getMessage());
        this.errorMessageCode = errorMessageCode;
        this.errors = errors != null ? errors : Collections.emptyList();
    }
}

