package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.service.PinService;
import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class PinsController {
    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private PinService pinService;

    /* 6. 모든 pin의 목록을 넘겨주는 API */
    @GetMapping("/pins")
    public Response<List<PinResponse>> getAllPins(@ApiIgnore @CurrentUser Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }

        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), pinService.getAllPins());
    }

    /* 7. 핀 등록하기 */
    @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public Response<Pins> createPin(@ModelAttribute CreatePinRequest createPinRequest, MultipartFile image, @ApiIgnore @CurrentUser Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }
        String imageUrl = null;

        if (image != null) {
            // upload profile to storage
            imageUrl = amazonS3ClientService.uploadFileToS3Bucket(image, true);
            Pins pin = pinService.createPin(createPinRequest, imageUrl);
            System.out.println("IMAGE_URL = " + imageUrl);
            return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), pin);
        } else {
            throw new BadRequestException("NO RESISTED IMAGE");
        }
    }

    /* 9. Pin detail 정보 보여주는 API */
    @GetMapping("/pin/{pinId}/detail")
    public Response<PinDetailResponse> getPinDetail(@PathVariable @NotBlank Long pinId, @ApiIgnore @CurrentUser Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.getPinDetail(pinId));
    }

    /* 11. Pin 수정하기 API */
    @PutMapping("/pin/{pinId}")
    public Response<Pins> modifyPin(@PathVariable @NotBlank Long pinId, PinRequest pinRequest, @ApiIgnore @CurrentUser Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), pinService.modifyPin(pinId, pinRequest));
    }

    /* 12. Pin 삭제하기 API */
    @DeleteMapping("/pin/{pinId}")
    public Response<String> deletePin(@PathVariable @NotBlank Long pinId, @ApiIgnore @CurrentUser Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }
        pinService.deletePin(pinId);
        return new Response<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), "SUCCESS DELETE");
    }
}
