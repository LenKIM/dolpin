package com.great.deploy.dolpin.exception;

public class ResourceNotFoundException extends DolpinException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public int getErrorCode() {
        return 4040;
    }

    public String getErrorMsg() {
        return "Not found Resource";
    }
}
