package com.great.deploy.dolpin.exception;

import com.great.deploy.dolpin.exception.model.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NonAuthorizationException.class)
    public ResponseEntity<ErrorInfo> nonAuthExceptionHandler(NonAuthorizationException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg(), exception.getData()));
    }

    @ExceptionHandler(value = InvalidStatusException.class)
    public ResponseEntity<ErrorInfo> notInvalidStatusExceptionHandler(InvalidStatusException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg(), exception.getData()));
    }

    @ExceptionHandler(value = NotSupportException.class)
    public ResponseEntity<ErrorInfo> notSupportExceptionHandler(NotSupportException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg(), exception.getData()));
    }

    @ExceptionHandler(value = OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<ErrorInfo> ResourceNotFound(OAuth2AuthenticationProcessingException exception) {
        return ResponseEntity
                .status(4001)
                .body(new ErrorInfo(4001, exception.getMessage()));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> ResourceNotFound(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg(), exception.getData()));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequest(BadRequestException exception) {
        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new ErrorInfo(exception.getErrorCode(), exception.getErrorMsg(), exception.getData()));
    }


}
