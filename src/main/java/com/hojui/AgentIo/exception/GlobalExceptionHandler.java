package com.hojui.AgentIo.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 모든 컨트롤러에서 던져진 예외를 여기서 받아 응답으로 바꿔준다
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 검사 실패 (@NotNull 위반 등) → 400 + 어떤 필드가 왜 틀렸는지
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> String.valueOf(f.getDefaultMessage()),
                        (a, b) -> a));
        return ResponseEntity.badRequest().body(errors);
    }
}