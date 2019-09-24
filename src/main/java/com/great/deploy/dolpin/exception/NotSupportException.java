package com.great.deploy.dolpin.exception;


public class NotSupportException extends DolpinException {

    public NotSupportException(String data) {
        super(data);
    }

    public NotSupportException(int code, String msg, String data) {
        super(code, msg, data);
    }

    public int getErrorCode() {
        return 5003;
    }

    public String getErrorMsg() {
        return "Not support function now";
    }
}