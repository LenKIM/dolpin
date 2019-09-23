package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class NonAuthorizationException extends DolpinException {


    public NonAuthorizationException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    public String getErrorMsg() {
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }
}
