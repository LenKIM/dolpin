package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends DolpinException {

    public ResourceNotFoundException(String data) {
        super(data);
    }

    public ResourceNotFoundException(int code, String msg, String data) {
        super(code, msg, data);
    }

    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    public String getErrorMsg() {
        return HttpStatus.NOT_FOUND.getReasonPhrase();
    }
}
