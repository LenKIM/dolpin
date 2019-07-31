package com.great.deploy.dolpin.controller.pins;

import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PinsController {
  @Autowired
  private AmazonS3ClientService amazonS3ClientService;


  @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void createPin(@RequestPart(value = "data", required = false) MultipartFile multipartFile){

    String profileImageUrl = null;
    if (multipartFile != null) {
      // upload profile to storage
      amazonS3ClientService.uploadFileToS3Bucket(multipartFile, true);
    }
  }
}
