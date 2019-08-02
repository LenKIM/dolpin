package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.model.Celebrites;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.service.CelebrityService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CelebrityController {
  private CelebrityService celebrityService;

  public CelebrityController(CelebrityService celebrityService) {
    this.celebrityService = celebrityService;
  }

  /* 아이돌 목록을 넘겨주는 API */
  @GetMapping("/celebrities")
  public Response<List<CelebrityResponse>> getCelebrities() {
    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), celebrityService.getCelebrities());
  }
}
