package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.AccessToken;
import com.great.deploy.dolpin.dto.Response;

public class AccessTokenResponse extends Response<AccessToken> {
    public AccessTokenResponse(int code, String msg, AccessToken data) {
        super(code, msg, data);
    }
}
