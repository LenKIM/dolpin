package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.swagger.FavoriteResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Api(value = "FavoriteController", description = "아이돌 즐겨찾기 관련 API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class FavoriteController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "특정 유저의 아이돌 목록 가져오기", response = FavoriteResponseModel.class)
    @GetMapping("/favorite/user")
    public Response<FavoriteResponse> getFavorites(@CurrentUser Account account) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new FavoriteResponse(account.getFavorite()));
    }

    @PostMapping("/favorite/user")
    @ApiOperation(value = "좋아하는 아이돌 저장하기", response = FavoriteResponseModel.class)
    public Response<AccountResponse> saveFavorites(@RequestBody Set<Favorite> Favorite, @CurrentUser Account account) {

        if (StringUtils.isEmpty(account)) throw new BadRequestException("No Account");

        Account savedAccount = Account.saveFavorites(account, Favorite);
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), Account.ofResponse(savedAccount));
    }
}