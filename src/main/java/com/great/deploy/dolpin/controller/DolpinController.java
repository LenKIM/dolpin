package com.great.deploy.dolpin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;
import com.great.deploy.dolpin.dto.model.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.NonAuthorizationException;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.repository.VisitRepository;
import com.great.deploy.dolpin.service.ReportService;
import com.great.deploy.dolpin.swagger.DolpinResponse;
import com.great.deploy.dolpin.swagger.NewCelebrityResponseSwagger;
import com.great.deploy.dolpin.swagger.ProofResponseSwagger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Optional;

@Api(value = "ReportController", description = "Report 관련 API")
@RestController
@RequestMapping(value = "/api/report", produces = "application/json")
public class DolpinController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @ApiOperation(value = "아이돌 Pins 방문 인증(Authentication)", response = ProofResponseSwagger.class)
    @PostMapping("/proof")
    public Response<ProofResponse> idolPinAuthentication(
            @ApiIgnore @CurrentUser Account account,
            @RequestBody ProofRequest request,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            throw new BadRequestException("null 체크 필요");
        }
        Account.validateAccount(account);

        Visit visit = reportService.proof(request);

        if (visit == Visit.NOT_FOUND) {
            throw new NonAuthorizationException("인증되지 않은 핀 정보입니다.");
        }
        Integer accountId = visit.getAccountId();
        Long pinCount = visitRepository.countVisitByPinId(visit.getPinId()).orElse(0L);
        Integer id = account.getId();
        if (!accountId.equals(id)) {
            throw new ResourceNotFoundException("잘못된 계정입니다.");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new ProofResponse(true, pinCount));
    }

    @ApiOperation(value = "새로운 아이돌 제보", response = NewCelebrityResponseSwagger.class)
    @PostMapping("/celebrity/new")
    public Response<NewCelebrityResponse> addNewCelebrity(
            @ApiIgnore @CurrentUser Account account,
            @RequestBody NewCelebrityRequest request,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            throw new BadRequestException("Not Allowed null and Empty String");
        }
        Account.validateAccount(account);
        String groupName = request.getGroupName();
        String memberList = String.join(", ", request.getMemberList());
        Boolean isSuccess = reportService.addNewCelebrity(groupName, memberList);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new NewCelebrityResponse(isSuccess));
    }

    @ApiOperation(value = "아이돌 광고 제보 API", response = DolpinResponse.class)
    @PostMapping("/dolpin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "DolpinRequest.class", required = true, dataType = "string", defaultValue = "{\"celebrity_member_id\":1,\"address\":\"서울시 강남구\",\"detailed_address\":\"강남역 8번 출구\",\"start_date\":\"2019-10-30\",\"end_date\":null, \"type\":none }"),
    })
    public DolpinResponse dolpinIdol(
            @RequestParam("model") String model,
            @RequestParam(value = "data") MultipartFile image,
            @ApiIgnore @CurrentUser Account account
    ) {
        DolpinRequest request = null;
        //TODO need to know how to deal with exception on Java8
        try {
            request = Optional
                    .ofNullable(objectMapper.readValue(model, DolpinRequest.class))
                    .orElse(DolpinRequest.EMPTY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO validation
        if (request == DolpinRequest.EMPTY || request == null) {
            throw new BadRequestException("Null 또는 빈칸 확인 필요");
        }
        Account.validateAccount(account);

        PostedAddress address = new PostedAddress(request.getAddress(), request.getDetailedAddress());
        PositingPeriod period = new PositingPeriod(request.getStartDate(), request.getEndDate());
        try {
            reportService.dolpin(request.getCelebrityMemberId(), address, period, image, request.getType());
            return new DolpinResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Boolean.TRUE);
        } catch (Exception e) {
            return new DolpinResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Boolean.FALSE);
        }
    }
}
