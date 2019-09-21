package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.AccessToken;
import com.great.deploy.dolpin.dto.AccessTokenResponse;
import com.great.deploy.dolpin.dto.Response;

public class AccessTokenResponseSwagger extends Response<AccessTokenResponse> {
    public AccessTokenResponseSwagger(int code, String msg, AccessTokenResponse data) {
        super(code, msg, data);
    }
}
