package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountService;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "AccountController", description = "유저 관련 API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "현재 유저 정보 가져오기")
    @GetMapping("/user")
    public Response<AccountResponse> getCurrentUserInfo(@CurrentUser Account account) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Account.ofResponse(account));
    }

    @ApiOperation(value = "현재 유저 정보 업데이트")
    @PutMapping("/user")
    public Response<AccountResponse> updateCurrentUserInfo(Account newAccount, @CurrentUser Account account) {
        if (account.getId() == null) {
            // 예외처리.
        }

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Account.ofResponse(Account.of(account.getId(), newAccount)));
    }


}
