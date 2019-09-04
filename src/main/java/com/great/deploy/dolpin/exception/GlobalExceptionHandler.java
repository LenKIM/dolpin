package com.great.deploy.dolpin.exception;

import com.great.deploy.dolpin.exception.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NonAuthorizationException.class)
    public ResponseEntity<ErrorInfo> nonAuthExceptionHandler(NonAuthorizationException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(exception.errorCode, exception.errorMsg));
    }

    @ExceptionHandler(value = InvalidStatusException.class)
    public ResponseEntity<ErrorInfo> nonAuthExceptionHandler(InvalidStatusException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorInfo(exception.errorCode, exception.errorMsg));
    }

    @ExceptionHandler(value = NotSupportException.class)
    public ResponseEntity<ErrorInfo> nonAuthExceptionHandler(NotSupportException exception){
        return ResponseEntity
                .status(4040)
                .body(new ErrorInfo(exception.errorCode, exception.errorMsg));
    }
}
