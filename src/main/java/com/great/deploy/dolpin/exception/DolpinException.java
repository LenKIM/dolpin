package com.great.deploy.dolpin.exception;

public class DolpinException extends RuntimeException {

    protected int code;
    protected String msg;
    protected String data;

    public DolpinException(String data) {
        this.data = data;
    }

    public DolpinException(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DolpinException(String message, int code, String msg, String data) {
        super(message);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DolpinException(String message, Throwable cause, int code, String msg, String data) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DolpinException(Throwable cause, int code, String msg, String data) {
        super(cause);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DolpinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg, String data) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
