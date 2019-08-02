package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.Celebrites;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.service.FavoriteService;
import com.great.deploy.dolpin.swagger.FavoriteResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "FavoriteController", description = "아이돌 즐겨찾기 관련 API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;
    @ApiOperation(value = "아이돌 목록 가져오기", response = FavoriteResponseModel.class)
    @GetMapping("/favorite/user/{userId}")
    public Response<FavoriteResponse> getFavorites(@PathVariable @NotBlank Long userId) {
        List<Favorite> favorites = favoriteService.getFavorites(userId);
        return getFavoriteResponse(favorites);
    }

    //    TODO memberId 와 GroupID를 List로 만들어 주는 코드가 필요한데, 자꾸 에러.
    @PostMapping("/favorite/user/{userId}")
    @ApiOperation(value = "아이돌 저장하기", response = FavoriteResponseModel.class)
    public Response<FavoriteResponse> saveFavorites(@PathVariable @NotBlank Long userId, @RequestBody Celebrites celebrites) {
        if (StringUtils.isEmpty(userId)) throw new BadRequestException("UserId 가 없음");

//        예외처리 만약 FavoriteResponse 안에 같은 데이터가 존재하면 삐익

        List<Favorite> favorites = favoriteService.saveCelebriteFavorites(userId, celebrites);
        return getFavoriteResponse(favorites);
    }


    private Response<FavoriteResponse> getFavoriteResponse(List<Favorite> favorites) {
        List<Celebrites> celebrites = favorites.stream().map(Favorite::getCelebrites).collect(Collectors.toList());
        FavoriteResponse response = new FavoriteResponse(celebrites);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), response);
    }
}