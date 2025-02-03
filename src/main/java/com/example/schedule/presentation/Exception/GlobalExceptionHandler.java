package com.example.schedule.presentation.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 *  Global Exception Handler
 * - @RestControllerAdvice : 모든 컨트롤러에서 발생하는 예외를 전역에서 처리
 * - @ExceptionHandler : 특정 예외를 잡아 커스텀 응답 반환
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ApplicationException 예외처리 핸들러
     * - 서비스 계층에서 발생한 ApplicationException을 처리하고, JSON 응답을 반환
     * @param ex : 발생한 ApplicationException 객체
     * @return : ResponseEntity<Map<String, Object>> 형태의 JSON 응답
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApplicationException(ApplicationException ex) {
        // 클라이언트에게 반환할 오류 응답 객체 생성
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", ex.getErrorMessageCode().getCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("errors", ex.getErrors());
        errorResponse.put("timestamp", LocalDateTime.now());

        // 에러 코드에 따라 적절한 HTTP 상태 코드 설정
        HttpStatus status = switch (ex.getErrorMessageCode()) {
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST; // 400: 잘못된 요청
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED; // 401: 인증 실패
            case FORBIDDEN -> HttpStatus.FORBIDDEN; // 403: 접근 권한 없음
            case NOT_FOUND -> HttpStatus.NOT_FOUND; // 404: 리소스를 찾을 수 없음
            case INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR; // 500: 서버 내부 오류
            default -> HttpStatus.INTERNAL_SERVER_ERROR; // 기본값: 500
        };
        // HTTP 상태 코드와 함께 JSON 응답 반환
        return ResponseEntity.status(status).body(errorResponse);
    }
}
