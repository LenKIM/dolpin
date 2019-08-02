package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.AccountReqeust;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.repository.AccountsRepository;
import com.great.deploy.dolpin.security.CurrentUser;
import com.great.deploy.dolpin.security.UserPrincipal;
import com.great.deploy.dolpin.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

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

    @GetMapping("/user/{userId}")
    public Response<Accounts> getUser(@PathVariable Long userId) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.getAccounts(userId));
    }

    @PostMapping("/user/{userId}")
    public Response<Accounts> updateUser(@PathVariable Long userId, @RequestBody AccountReqeust accountReqeust) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.updateAccounts(userId, accountReqeust));
    }

    @DeleteMapping("/user/{userId}")
    public Response<Boolean> updateUser(@PathVariable @NotBlank Long userId) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), accountsService.deleteAccounts(userId));
    }
}
