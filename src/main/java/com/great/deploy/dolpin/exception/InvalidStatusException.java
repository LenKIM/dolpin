package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class InvalidStatusException extends DolpinException {

    public InvalidStatusException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public int getErrorCode() {
        return HttpStatus.CONFLICT.value();
    }

    public String getErrorMsg() {
        return HttpStatus.CONFLICT.getReasonPhrase();
    }
}