package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<CustomError> handleNotFoundException(RuntimeException ex) {
        log.info("NotFountException occurred");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomError.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({FillTheBlanksException.class})
    public ResponseEntity<CustomError> handleFillTheBlanksException(RuntimeException ex) {
        log.info("FillTheBlanksException occurred");
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED)
                .body(CustomError.builder()
                        .status(HttpStatus.NOT_EXTENDED.value())
                        .error(HttpStatus.NOT_EXTENDED.getReasonPhrase())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({AlreadyCreatedException.class})
    public ResponseEntity<CustomError> alreadyCreatedException(RuntimeException ex) {
        log.info("AlreadyCreatedException occurred");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CustomError.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({PermissionException.class})
    public ResponseEntity<CustomError> permissionException(RuntimeException ex) {
        log.info("PermissionException occurred");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(CustomError.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({InterviewFailException.class})
    public ResponseEntity<CustomError> interviewFailException(RuntimeException ex) {
        log.info("InterviewFailException occurred");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(CustomError.builder()
                        .status(HttpStatus.EXPECTATION_FAILED.value())
                        .error(HttpStatus.EXPECTATION_FAILED.getReasonPhrase())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
