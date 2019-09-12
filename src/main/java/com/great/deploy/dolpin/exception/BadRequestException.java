package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DolpinException {


    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}