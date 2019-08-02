package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.model.Accounts;

public class AccountModel extends Response<Accounts> {

    public AccountModel(int code, String msg, Accounts data) {
        super(code, msg, data);
    }
}
