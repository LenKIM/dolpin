package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class InvalidStatusException extends DolpinException {

    public InvalidStatusException(String data) {
        super(data);
    }

    public InvalidStatusException(int code, String msg, String data) {
        super(code, msg, data);
    }

    public int getErrorCode() {
        return HttpStatus.CONFLICT.value();
    }

    public String getErrorMsg() {
        return HttpStatus.CONFLICT.getReasonPhrase();
    }
}