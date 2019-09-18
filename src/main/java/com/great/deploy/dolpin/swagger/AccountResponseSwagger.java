package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.Response;

public class AccountResponseSwagger extends Response<AccountResponse> {
    public AccountResponseSwagger(int code, String msg, AccountResponse data) {
        super(code, msg, data);
    }
}
