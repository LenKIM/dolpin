package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.service.CelebrityService;
import com.great.deploy.dolpin.swagger.CelebrityListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "CelebrityController", description = "아이돌 목록 관련 API")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CelebrityController {
    private CelebrityService celebrityService;

    public CelebrityController(CelebrityService celebrityService) {
        this.celebrityService = celebrityService;
    }

    @ApiOperation(value = "아이돌 목록 가져오기", response = CelebrityListResponse.class)
    @GetMapping("/celebrities")
    public Response<List<CelebrityResponse>> getCelebrities(@ApiIgnore @CurrentUser Account account) {
        if(account == null){
            throw new BadRequestException("No found user");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), celebrityService.getCelebrities());
    }
}

