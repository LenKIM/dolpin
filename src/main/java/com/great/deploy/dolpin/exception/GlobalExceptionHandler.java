package com.great.deploy.dolpin.exception;

import com.great.deploy.dolpin.exception.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NonAuthorizationException.class)
    public ResponseEntity<ErrorInfo> nonAuthExceptionHandler(NonAuthorizationException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(value = InvalidStatusException.class)
    public ResponseEntity<ErrorInfo> notInvalidStatusExceptionHandler(InvalidStatusException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg()));
    }

    @ExceptionHandler(value = NotSupportException.class)
    public ResponseEntity<ErrorInfo> notSupportExceptionHandler(NotSupportException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg()));
    }

    @ExceptionHandler(value = OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<ErrorInfo> OAuth2Exception(OAuth2AuthenticationProcessingException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(1401, exception.getMessage()));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> OAuth2Exception(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(1401, exception.getMessage()));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequest(BadRequestException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getMessage()));
    }


}
