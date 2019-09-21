package com.great.deploy.dolpin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.component.PinValidator;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.NotSupportException;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.service.PinService;
import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pins", produces = "application/json")
public class PinsController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private PinService pinService;

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @Autowired
    PinValidator pinValidator;

    @GetMapping
    public Response<List<PinResponse>> getAllPins(@ApiIgnore @CurrentUser Account account) {

        Account.validateAccount(account);
        return new Response<>(
                HttpStatus.ACCEPTED.value(),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                pinService.getAllPins(account.getId())
        );
    }

    @PostMapping(value = "/pin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public Response<Pins> createPin(
            @RequestParam("model") String model,
            @RequestParam(value = "data") MultipartFile image,
            @ApiIgnore @CurrentUser Account account) throws IOException {

        Account.validateAccount(account);

        CreatePinRequest createPinRequest = Optional
                .ofNullable(objectMapper.readValue(model, CreatePinRequest.class))
                .orElse(CreatePinRequest.EMPTY);

        if (createPinRequest == CreatePinRequest.EMPTY) {
            throw new ResourceNotFoundException("Not Found createPinRequest Model");
        }
        String imageUrl;
        if (image != null) {
            // upload profile to storage
            imageUrl = amazonS3ClientService.uploadFileToS3Bucket(image, true);

            CelebrityMember celebrityMember = celebrityMemberRepository
                    .findById(createPinRequest.getMemberId())
                    .orElse(null);

            if (celebrityMember == null) {
                throw new NotSupportException(HttpStatus.NOT_FOUND.value(), "Not Found Member Id");
            }

            CelebrityGroup celebrityGroup = celebrityGroupRepository
                    .findById(createPinRequest.getGroupId())
                    .orElse(null);

            if (celebrityGroup == null) {
                throw new NotSupportException(HttpStatus.NOT_FOUND.value(), "Not Found Group Id");
            }

            Pins pin = pinService.createPin(
                    Pins.of(
                            createPinRequest, celebrityMember, celebrityGroup, createPinRequest.getAddress(), createPinRequest.getDetailedAddress()
                    ), imageUrl);

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
    public Response<PinResponse> getPinDetail(
            @PathVariable @NotBlank Long pinId,
            @ApiIgnore @CurrentUser Account account
    ) {
        Account.validateAccount(account);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                pinService.getPinDetail(pinId, account.getId())
        );
    }

    @PutMapping("/pin/{pinId}")
    public Response<Pins> modifyPin(
            @PathVariable @NotBlank Long pinId,
            @RequestBody @Valid PinRequest pinRequest, Errors errors,
            @ApiIgnore @CurrentUser Account account) {

        if (errors.hasErrors()) {
            throw new BadRequestException("fail to modify pin");
        }

        pinValidator.validate(pinRequest, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException("fail to modify pin");
        }

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

    @ApiOperation("pin 들중에 특정 연예인 정보")
    @PostMapping("/pin/celebrity")
    public Response<List<PinResponse>> getCelebrities(
            @ApiIgnore @CurrentUser Account account,
            @RequestBody CelebrityRequest celebrityRequest
    ) {
        Account.validateAccount(account);

        Long celebrityId = celebrityRequest.getCelebrityId();
        CelebrityType celebrityType = celebrityRequest.getCelebrityType();
        if (celebrityType.equals(CelebrityType.MEMBER)) {
            List<PinResponse> memberPins = pinService.getMemberPins(celebrityId, account.getId());
            return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), memberPins);
        } else if (celebrityType.equals(CelebrityType.GROUP)) {
            List<PinResponse> groupPins = pinService.getGroupPins(celebrityId, account.getId());
            return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), groupPins);
        } else {
            throw new ResourceNotFoundException("Not Found celebrityRequest");
        }
    }
}
