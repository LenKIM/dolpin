package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.ClosePinResponse;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinInfo;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.service.PinService;
import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  /* 6. 모든 pin의 목록을 넘겨주는 API */
  @GetMapping("/pins")
  public Response<List<PinResponse>> getAllPins() {
    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.getAllPins());
  }

//  /* 7. 핀 등록하기 */
//  @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  public Response<Pins> createPin(@RequestBody CreatePinRequest createPinRequest, MultipartFile image){
//    String imageUrl = null;
//
//    if ( image != null) {
//      // upload profile to storage
//       imageUrl = amazonS3ClientService.uploadFileToS3Bucket(image, true);
//       pinService.createPin(createPinRequest, imageUrl);
//      System.out.println("등록된 이미지 url은 " + imageUrl);
//      return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.createPin(createPinRequest, imageUrl));
//    } else {
//      return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.createPin(createPinRequest, imageUrl));
//    }
//  }

  /* 9. Pin detail 정보 보여주는 API */
  @GetMapping("/pin/{pinId}/detail")
  public Response<PinDetailResponse> getPinDetail(@PathVariable @NotBlank Long pinId){
    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.getPinDetail(pinId));
  }

//  /* 11. Pin 수정하기 API */
//  @PutMapping("/pin/{pinId}")
//  public Response<PinInfo> modifyPin(@PathVariable @NotBlank Long pinId, PinInfo pinInfo) {
//    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.modifyPin(pinId, pinInfo));
//  }

  /* 12. Pin 삭제하기 API */
  @DeleteMapping("/pin/{pinId}")
  public Response<String> deletePin(@PathVariable @NotBlank Long pinId) {
    pinService.deletePin(pinId);
    return new Response<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), "true");
  }
}
