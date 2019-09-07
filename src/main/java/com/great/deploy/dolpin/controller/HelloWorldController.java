package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiOperation(value = "Test OAuth Authorization for Client")
@RestController
public class HelloWorldController {

    @GetMapping("/ping")
    public String getCurrentUser(@ApiIgnore @CurrentUser Account account) {
        Account.validateAccount(account);
        return "pong";
    }
}
