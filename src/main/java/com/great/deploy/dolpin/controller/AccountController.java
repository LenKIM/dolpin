package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.swagger.AccessTokenResponse;
import com.great.deploy.dolpin.swagger.AccountResponseSwagger;
import com.great.deploy.dolpin.swagger.CheckEmailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static com.great.deploy.dolpin.account.Account.validateAccount;

@Api(value = "AccountController", description = "About USER API")
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @ApiOperation(value = "Get Current User Info", response = AccountResponseSwagger.class)
    @GetMapping
    public Response<AccountResponse> getCurrentUserInfo(@ApiIgnore @CurrentUser Account account) {
        validateAccount(account);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                Account.ofResponse(account)
        );
    }

    @ApiOperation(value = "Update Current User Info", response = AccountResponseSwagger.class)
    @PutMapping
    public Response<AccountResponse> updateCurrentUserInfo(
            @RequestBody AccountUpdateRequest newAccount,
            @ApiIgnore @CurrentUser Account oldAccount) {

        validateAccount(oldAccount);

        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                Account.ofResponse(Account.of(oldAccount, newAccount))
        );
    }

    @ApiOperation(value = "create user by email and sns", response = AccessTokenResponse.class)
    @PostMapping("/create")
    public Response<AccessToken> createUser(
            @RequestBody AccountRequest accountRequest
    ) {
        AccessToken account = accountService.create(
                accountRequest.getEmail(),
                accountRequest.getNickname(),
                accountRequest.getFavorites(),
                accountRequest.getType());

        return new Response<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                account
        );
    }

    @ApiOperation(value = "login user by email if user existed", response = AccessTokenResponse.class)
    @PostMapping("/login")
    public Response<AccessToken> loginUser(
            @RequestBody String email
    ) {
        AccessToken account = accountService.login(email);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                account
        );
    }

    @ApiOperation(value = "check existed user email", response = CheckEmailResponse.class)
    @GetMapping("/check")
    public Response<Boolean> existedUser(
            @RequestParam String email
    ) {
        //TODO email 인증.
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), accountRepository.existsByEmail(email));
    }

}
