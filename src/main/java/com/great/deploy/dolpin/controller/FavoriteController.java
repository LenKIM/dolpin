package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.FavoriteRequest;
import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.model.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.service.FavoriteService;
import com.great.deploy.dolpin.swagger.FavoriteResponseSwagger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.stream.Collectors;

import static com.great.deploy.dolpin.account.Account.validateAccount;

@Api(value = "FavoriteController", description = "About User's Idol Favorites API")
@RestController
@RequestMapping(value = "/api/favorite/user", produces = "application/json")
public class FavoriteController {

    @Autowired
    AccountService accountService;
    @Autowired
    FavoriteService favoriteService;

    @ApiOperation(value = "Get current user's favorites Info", response = FavoriteResponseSwagger.class)
    @GetMapping()
    public Response<FavoriteResponse> getFavorites(@ApiIgnore @CurrentUser Account account) {
        validateAccount(account);

        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                new FavoriteResponse(accountService.getFavorite(account.getId())));
    }

    @PutMapping
    @ApiOperation(value = "Update current user's favorites Info", response = FavoriteResponseSwagger.class)
    public Response<FavoriteResponse> updateFavorites(@RequestBody FavoriteRequest favorites,
                                                      @ApiIgnore @CurrentUser Account account,
                                                      Errors errors) {
        validateAccount(account);
        if (errors.hasErrors()){
            throw new BadRequestException("null 체크 필요");
        }

        boolean validateGroups = favoriteService.isValidateGroups(favorites.getFavorites().stream()
                .map(Favorite::getGroupId).collect(Collectors.toList()));
        boolean validateMembers = favoriteService.isValidateMembers(favorites.getFavorites().stream()
                .map(Favorite::getMemberId).collect(Collectors.toList()));

        if(!(validateGroups || validateMembers)){
            throw new ResourceNotFoundException("유효하지 않는 연예인 정보 입니다.");
        }
        Account savedAccount = Account.changeFavorites(account, favorites.getFavorites());
        Account newAccount = accountService.updateAccount(savedAccount);
        FavoriteResponse favoriteResponse = new FavoriteResponse(newAccount.getFavorites());
        return new Response<>(
                HttpStatus.ACCEPTED.value(),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                favoriteResponse
        );
    }
}