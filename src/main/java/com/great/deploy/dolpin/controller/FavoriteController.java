package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.Celebrites;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/favorite/user/{userId}")
    public Response<FavoriteResponse> getFavorites(@PathVariable @NotBlank Long userId) {
        List<Favorite> favorites = favoriteService.getFavorites(userId);
        return getFavoriteResponseResponse(favorites);
    }

    //    TODO memberId 와 GroupID를 List로 만들어 주는 코드가 필요한데, 자꾸 에러.
    @PostMapping("/favorite/user/{userId}")
    public Response<FavoriteResponse> saveFavorites(@PathVariable @NotBlank Long userId, @RequestBody Celebrites celebrites) {
        if (StringUtils.isEmpty(userId)) throw new BadRequestException("UserId 가 없음");

//        예외처리 만약 FavoriteResponse 안에 같은 데이터가 존재하면 삐익

        List<Favorite> favorites = favoriteService.saveCelebriteFavorites(userId, celebrites);
        return getFavoriteResponseResponse(favorites);
    }

    private Response<FavoriteResponse> getFavoriteResponseResponse(List<Favorite> favorites) {
        List<Celebrites> celebrites2 = favorites.stream().map(Favorite::getCelebrites).collect(Collectors.toList());
        FavoriteResponse response = new FavoriteResponse(celebrites2);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), response);
    }
}