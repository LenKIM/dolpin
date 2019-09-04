package com.great.deploy.dolpin.exception;

public class DolpinException extends RuntimeException {

    int errorCode;
    String errorMsg;

    public DolpinException(int errorCode, String message, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DolpinException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DolpinException(String message, int errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DolpinException(String message, Throwable cause, int errorCode, String errorMsg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DolpinException(Throwable cause, int errorCode, String errorMsg) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DolpinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorCode, String errorMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
