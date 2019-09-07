package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static com.great.deploy.dolpin.account.Account.validateAccount;

@Api(value = "AccountController", description = "About USER API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountController {

    @Autowired
    AccountService accountService;


    @ApiOperation(value = "Get Current User Info")
    @GetMapping("/user")
    public Response<AccountResponse> getCurrentUserInfo(@ApiIgnore @CurrentUser Account account) {
        validateAccount(account);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                Account.ofResponse(account)
        );
    }

    @ApiOperation(value = "Update Current User Info")
    @PutMapping("/user")
    public Response<AccountResponse> updateCurrentUserInfo(
            @ApiParam(value = "New Account you want to insert") @RequestBody AccountRequest newAccount,
            @ApiIgnore @CurrentUser Account account) {

        validateAccount(account);

        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                Account.ofResponse(Account.of(account, newAccount))
        );
    }
}
