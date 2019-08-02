package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.repository.AccountsRepository;
import com.great.deploy.dolpin.security.CurrentUser;
import com.great.deploy.dolpin.security.UserPrincipal;
import com.great.deploy.dolpin.service.AccountsService;
import com.great.deploy.dolpin.swagger.AccountModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(value = "UserController", description = "User 관련 API")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AccountsRepository accounts;

    @Autowired
    private AccountsService accountsService;


    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Accounts getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return accounts.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userPrincipal.getId()));
    }

    @ApiOperation(value = "유저 가져오기", response = AccountModel.class)
    @GetMapping("/user/{userId}")
    public Response<Accounts> getUser(@PathVariable Long userId) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.getAccounts(userId));
    }
    @ApiOperation(value = "유저 정보 수정", response = AccountModel.class)
    @PostMapping("/user/{userId}")
    public Response<Accounts> updateUser(@PathVariable Long userId, @RequestBody AccountRequest accountRequest) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.updateAccounts(userId, accountRequest));
    }
    @ApiOperation(value = "유저 정보 삭제", response = AccountModel.class)
    @DeleteMapping("/user/{userId}")
    public Response<Boolean> updateUser(@PathVariable @NotBlank Long userId) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.deleteAccounts(userId));
    }
}
