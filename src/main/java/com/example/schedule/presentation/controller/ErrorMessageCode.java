package com.example.schedule.presentation.controller;

public enum ErrorMessageCode {
    OK("2000", "ok", null),
    BAD_REQUEST("4000", "잘못된 요청입니다.", "ko"),
    UNAUTHORIZED("4001", "인증이 필요합니다.", "ko"),
    FORBIDDEN("4003", "권한이 없습니다.", "ko"),
    NOT_FOUND("4004", "요청한 리소스를 찾을 수 없습니다.", "ko"),
    INTERNAL_SERVER_ERROR("5000", "서버 내부 오류가 발생했습니다.", "ko");

    private final String code;
    private final String message;
    private final String languageCode;

    ErrorMessageCode(String code, String message, String languageCode) {
        this.code = code;
        this.message = message;
        this.languageCode = languageCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}