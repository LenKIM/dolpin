package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.AccessToken;
import com.great.deploy.dolpin.dto.Response;

public class AccessTokenResponseSwagger extends Response<AccessToken> {
    public AccessTokenResponseSwagger(int code, String msg, AccessToken data) {
        super(code, msg, data);
    }
}
