package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "AccountController", description = "유저 관련 API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountController {

    @Autowired
    AccountService accountService;


    @ApiOperation(value = "현재 유저 정보 가져오기")
    @GetMapping("/user")
    public Response<AccountResponse> getCurrentUserInfo(@ApiIgnore @CurrentUser Account account) {
        if(account == null){
            throw new BadRequestException("No found user");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Account.ofResponse(account));
    }

    @ApiOperation(value = "현재 유저 정보 업데이트")
    @PutMapping("/user")
    public Response<AccountResponse> updateCurrentUserInfo(Account newAccount, @ApiIgnore @CurrentUser Account account) {
        if(account == null){
            throw new BadRequestException("No found user");
        }

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Account.ofResponse(Account.of(account.getId(), newAccount)));
    }


}
