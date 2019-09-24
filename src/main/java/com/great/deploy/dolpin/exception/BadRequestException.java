package com.great.deploy.dolpin.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DolpinException {


    public BadRequestException(String data) {
        super(data);
    }

    public BadRequestException(int code, String msg, String data) {
        super(code, msg, data);
    }

    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public String getErrorMsg() {
        return HttpStatus.BAD_REQUEST.getReasonPhrase();
    }
}