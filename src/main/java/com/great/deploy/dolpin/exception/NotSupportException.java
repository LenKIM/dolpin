package com.great.deploy.dolpin.exception;


public class NotSupportException extends DolpinException {

    public NotSupportException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public int getErrorCode() {
        return 5003;
    }

    public String getErrorMsg() {
        return "Not support function now";
    }
}