package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.ClosePinResponse;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.service.PinService;
import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PinsController {
  @Autowired
  private AmazonS3ClientService amazonS3ClientService;

  @Autowired
  private PinService pinService;


  @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void createPin(@RequestPart(value = "data", required = false) MultipartFile multipartFile){

    String profileImageUrl = null;
    if (multipartFile != null) {
      // upload profile to storage
      amazonS3ClientService.uploadFileToS3Bucket(multipartFile, true);
    }
  }

  /* 6. 모든 pin의 목록을 넘겨주는 API */
  @GetMapping("/pins")
  public Response<List<PinResponse>> getAllPins() {
    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.getAllPins());
  }

  /* 반경 100m 내의 핀을 보여주는 API */
//  @GetMapping("/pins/pin/closer")
//  public Response<Page<ClosePinResponse>> getClosePins(
//      @RequestParam Double latitude,
//      @RequestParam Double longitude,
//      @RequestParam int start
//  ){
//    Pageable pageable = PageRequest.of(start, 20);
//    Page<ClosePinResponse> closePinResponses = pinService.getClosePins(latitude, latitude, pageable);
//
//  }

  /* 9. Pin detail 정보 보여주는 API */
  @GetMapping("/pin/{pinId}/detail")
  public Response<PinDetailResponse> getPinDetail(@PathVariable @NotBlank Long pinId){
    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.getPinDetail(pinId));
  }

}
