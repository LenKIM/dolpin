package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.model.Response;

public class CheckEmailResponse extends Response<Boolean> {
    public CheckEmailResponse(int code, String msg, Boolean data) {
        super(code, msg, data);
    }
}
