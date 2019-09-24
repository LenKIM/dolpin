package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class NonAuthorizationException extends DolpinException {

    public NonAuthorizationException(String data) {
        super(data);
    }

    public NonAuthorizationException(int code, String msg, String data) {
        super(code, msg, data);
    }

    public int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    public String getErrorMsg() {
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }
}
