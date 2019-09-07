package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.exception.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/ping")
    public String getCurrentUser(@ApiIgnore @CurrentUser Account account) {
        if(account == null){
            throw new BadRequestException("No found user");
        }
        return "pong " + account.getEmail();
    }
}
