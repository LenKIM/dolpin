package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.dto.Response;

public class AccountModel extends Response<Account> {

    public AccountModel(int code, String msg, Account data) {
        super(code, msg, data);
    }
}
