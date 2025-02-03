package com.example.schedule.presentation.Exception;

import java.util.Collections;
import java.util.List;

public class ApplicationException extends RuntimeException {
    private final ErrorMessageCode errorMessageCode;
    private List<ApiError> errors;

    public ApplicationException(ErrorMessageCode errorMessageCode) {
        super(errorMessageCode.getMessage());
        this.errorMessageCode = errorMessageCode;
    }

    // 추가적인 오류 목록을 포함하는 생성자
    public ApplicationException(ErrorMessageCode errorMessageCode, List<ApiError> errors) {
        super(errorMessageCode.getMessage());
        this.errorMessageCode = errorMessageCode;
        this.errors = errors != null ? errors : Collections.emptyList();
    }

    public ErrorMessageCode getErrorMessageCode() {
        return errorMessageCode;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
}

