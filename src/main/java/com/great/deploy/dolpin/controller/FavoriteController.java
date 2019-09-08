package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.swagger.FavoriteResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;

import static com.great.deploy.dolpin.account.Account.validateAccount;

@Api(value = "FavoriteController", description = "About User's Idol Favorites API")
@RestController
@RequestMapping(value = "/api/favorite/user", produces = "application/json")
public class FavoriteController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "Get current user's favorites Info", response = FavoriteResponseModel.class)
    @GetMapping()
    public Response<FavoriteResponse> getFavorites(@ApiIgnore @CurrentUser Account account) {
        validateAccount(account);
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new FavoriteResponse(account.getFavorite()));
    }

    @PostMapping()
    @ApiOperation(value = "Post current user's favorites Info", response = FavoriteResponseModel.class)
    public Response<AccountResponse> saveFavorites(@RequestBody Set<Favorite> favorites,
                                                   @ApiIgnore @CurrentUser Account account) {
        validateAccount(account);
        if(favorites.size() == 0) {
            throw new BadRequestException("No favorites in Request");
        }

        Account savedAccount = Account.saveFavorites(account, favorites);

        return new Response<>(
                HttpStatus.ACCEPTED.value(),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                Account.ofResponse(savedAccount)
        );
    }
}