package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.Response;

public class DolpinResponse extends Response<Boolean>{

    public DolpinResponse(int code, String msg, Boolean data) {
        super(code, msg, data);
    }
}
