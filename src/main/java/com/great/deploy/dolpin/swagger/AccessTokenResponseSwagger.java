package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.AccountWithTokenResponse;
import com.great.deploy.dolpin.dto.model.Response;

public class AccessTokenResponseSwagger extends Response<AccountWithTokenResponse> {
    public AccessTokenResponseSwagger(int code, String msg, AccountWithTokenResponse data) {
        super(code, msg, data);
    }
}
