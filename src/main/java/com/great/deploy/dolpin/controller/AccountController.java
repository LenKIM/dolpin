package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.dto.model.Provider;
import com.great.deploy.dolpin.dto.model.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.swagger.AccessTokenResponseSwagger;
import com.great.deploy.dolpin.swagger.AccountResponseSwagger;
import com.great.deploy.dolpin.swagger.DolpinResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;

import static com.great.deploy.dolpin.account.Account.validateAccount;

@Api(value = "AccountController", description = "About USER API")
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(getClass());

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

    @ApiOperation(value = "createUser user by email and sns", response = AccessTokenResponseSwagger.class)
    @PostMapping("/create")
    public Response<AccountWithTokenResponse> createUser(
            @Valid @RequestBody AccountRequest accountRequest,
            Errors errors

    ) {
        if (errors.hasErrors()) {
            throw new BadRequestException("Null 체크 또는 이미 계정이 있음.");
        }
        AccountWithToken account = accountService.createUser(
                accountRequest.getEmail(),
                accountRequest.getNickname(),
                accountRequest.getFavorites(),
                accountRequest.getSnsType(),
                accountRequest.getSnsId());

        return new Response<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                AccountWithTokenResponse.of(account)
        );
    }

    @ApiOperation(value = "login user by email if user existed", response = AccessTokenResponseSwagger.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 4001, message = "로그인 실패")})
    @PostMapping("/login")
    public Response<AccountWithTokenResponse> loginUser(
            @RequestBody LoginRequest request
    ) {
        String email = request.getEmail();
        Provider snsType = request.getSnsType();
        String snsId = request.getSnsId();
        AccountWithToken account = accountService.login(email, snsType, snsId);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                AccountWithTokenResponse.of(account)
        );
    }

    @ApiOperation(value = "Check Nickname Duplicate", response = DolpinResponse.class)
    @GetMapping("/exist/nickname")
    public DolpinResponse existNickname(@RequestParam String nickname) {
        return new DolpinResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountService.checkDuplicatedNickName(nickname));
    }

    @ApiOperation(value = "check existed user", response = ExistResponse.class)
    @PostMapping("/exist")
    public Response<ExistResponse> existedUser(
            @RequestBody LoginRequest request,
            Errors errors
    ) {
        String oauthId = AccountService.getOauthId(request.getEmail(), request.getSnsType(), request.getSnsId());

        if(errors.hasErrors()){

        }
        Account account = Optional.ofNullable(accountRepository.findByOauthId(oauthId)).orElse(Account.EMPTY);
        ExistResponse existResponse;
        if (account == Account.EMPTY) existResponse = new ExistResponse(false, Provider.NONE);
        else existResponse = new ExistResponse(true, account.getSnsType());
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), existResponse);
    }
}
