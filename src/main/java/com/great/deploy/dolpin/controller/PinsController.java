package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.NotSupportException;
import com.great.deploy.dolpin.model.CelebrityGroup;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
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
@RequestMapping(value = "/api/pins", produces = "application/json")
public class PinsController {

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private PinService pinService;

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @GetMapping()
    public Response<List<PinResponse>> getAllPins(@ApiIgnore @CurrentUser Account account) {

        Account.validateAccount(account);

        return new Response<>(
                HttpStatus.ACCEPTED.value(),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                pinService.getAllPins()
        );
    }

    @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public Response<Pins> createPin(
            @ModelAttribute CreatePinRequest createPinRequest,
            MultipartFile image,
            @ApiIgnore @CurrentUser Account account
    ) {
        Account.validateAccount(account);
        String imageUrl = null;

        if (image != null) {
            // upload profile to storage
            imageUrl = amazonS3ClientService.uploadFileToS3Bucket(image, true);

            CelebrityMember celebrityMember = celebrityMemberRepository
                    .findById(createPinRequest.getMemberId())
                    .orElse(null);

            if(celebrityMember == null){
                throw new NotSupportException(HttpStatus.NOT_FOUND.value(), "Not Found Member Id");
            }

            CelebrityGroup celebrityGroup = celebrityGroupRepository
                    .findById(createPinRequest.getGroupId())
                    .orElse(null);

            if(celebrityGroup == null){
                throw new NotSupportException(HttpStatus.NOT_FOUND.value(), "Not Found Group Id");
            }
            Pins pin = pinService.createPin(Pins.of(createPinRequest, celebrityMember, celebrityGroup), imageUrl);

            System.out.println("IMAGE_URL = " + imageUrl);

            return new Response<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    pin
            );
        } else {
            throw new BadRequestException("NO RESISTED IMAGE");
        }
    }

    @GetMapping("/pin/{pinId}/detail")
    public Response<PinDetailResponse> getPinDetail(
            @PathVariable @NotBlank Long pinId,
            @ApiIgnore @CurrentUser Account account
    ) {
        Account.validateAccount(account);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                pinService.getPinDetail(pinId)
        );
    }

    @PutMapping("/pin/{pinId}")
    public Response<Pins> modifyPin(
            @PathVariable @NotBlank Long pinId,
            @RequestBody PinRequest pinRequest,
            @ApiIgnore @CurrentUser Account account) {

        Account.validateAccount(account);

        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                pinService.modifyPin(pinId, pinRequest)
        );
    }

    @DeleteMapping("/pin/{pinId}")
    public Response<String> deletePin(
            @PathVariable @NotBlank Long pinId,
            @ApiIgnore @CurrentUser Account account
    ) {
        Account.validateAccount(account);

        pinService.deletePin(pinId);
        return new Response<>(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NO_CONTENT.getReasonPhrase(),
                "SUCCESS DELETE"
        );
    }
}
