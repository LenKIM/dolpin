package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.model.Response;
import com.great.deploy.dolpin.service.CelebrityService;
import com.great.deploy.dolpin.swagger.CelebrityListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "CelebrityController", description = "아이돌 목록 관련 API")
@RestController
@RequestMapping(value = "/api/celebrities", produces = "application/json")
public class CelebrityController {

    @Autowired
    private CelebrityService celebrityService;

    @ApiOperation(value = "아이돌 목록 가져오기", response = CelebrityListResponse.class)
    @GetMapping
    public Response<List<CelebrityResponse>> getCelebrities() {
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                celebrityService.getCelebrities()
        );
    }
}

