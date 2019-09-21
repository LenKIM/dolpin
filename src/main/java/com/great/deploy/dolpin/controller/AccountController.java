package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.model.Provider;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.swagger.AccessTokenResponseSwagger;
import com.great.deploy.dolpin.swagger.AccountResponseSwagger;
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
                Account.ofResponse(
                        accountService.updateAccount(Account.of(oldAccount, newAccount))
                )
        );
    }

    @ApiOperation(value = "create user by email and sns", response = AccessTokenResponseSwagger.class)
    @PostMapping("/create")
    public Response<AccessTokenResponse> createUser(
            @RequestBody AccountRequest accountRequest
    ) {
        AccessToken account = accountService.create(
                accountRequest.getEmail(),
                accountRequest.getNickname(),
                accountRequest.getFavorites(),
                accountRequest.getSnsType(),
                accountRequest.getSnsId());

        return new Response<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                AccessTokenResponse.of(account)
        );
    }

    @ApiOperation(value = "login user by email if user existed", response = AccessTokenResponseSwagger.class)
    @PostMapping("/login")
    public Response<AccessTokenResponse> loginUser(
            @RequestBody LoginRequest request
    ) {
        String oauthId = AccountService.getOauthId(request.getEmail(), request.getSnsType(), request.getSnsId());
        AccessToken account = accountService.login(oauthId);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                AccessTokenResponse.of(account)
        );
    }

    @ApiOperation(value = "check existed user", response = ExistResponse.class)
    @PostMapping("/exist")
    public Response<ExistResponse> existedUser(
            @RequestBody LoginRequest request
    ) {
        String oauthId = AccountService.getOauthId(request.getEmail(), request.getSnsType(), request.getSnsId());
        Account account = accountRepository.findByOauthId(oauthId);
        ExistResponse existResponse;
        if (account == null) {
            existResponse = new ExistResponse(false, Provider.NONE);
        } else {
            existResponse = new ExistResponse(true, account.getSnsType());
        }
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), existResponse);
    }

}
