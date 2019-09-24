package com.great.deploy.dolpin.exception.model;

public class ErrorInfo {

    private int code;
    private String msg;
    private String data;

    public ErrorInfo(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ErrorInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
