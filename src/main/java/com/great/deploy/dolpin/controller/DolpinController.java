package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.DolpinRequest;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Celebrity;
import com.great.deploy.dolpin.model.PositingPeriod;
import com.great.deploy.dolpin.model.PostedAddress;
import com.great.deploy.dolpin.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "ReportController", description = "Report 관련 API")
@RestController
@RequestMapping("/api/report")
public class DolpinController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "아이돌 Pins 방문 인증(Authentication)")
    @PostMapping("/proof")
    public Response<ProofResponse> idolPinAuthentication(
            @ApiIgnore @CurrentUser Account account,
            @RequestBody ProofRequest request
    ) {
        Account.validateAccount(account);
        Visit proof = reportService.proof(request);
        Integer accountId = proof.getAccountId();
        Integer id = account.getId();
        if (!accountId.equals(id)) {
            throw new ResourceNotFoundException("Not Matching pin account Id");
        }
        ProofResponse response = new ProofResponse(true);
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), response);
    }

    @ApiOperation(value = "아이돌 광고 제보 API")
    @PostMapping("/dolpin")
    public Response<Boolean> dolpinIdol(
            @ApiIgnore @CurrentUser Account account,
            @RequestBody DolpinRequest request
    ) {
        Account.validateAccount(account);
        Celebrity celebrity = new Celebrity(request.getCelebrityId(), request.getCelebrityType());
        PostedAddress address = new PostedAddress(request.getAddress(), request.getDetailedAddress());
        PositingPeriod period = new PositingPeriod(request.getStartDate(), request.getEndDate());
        reportService.dolpin(celebrity, address, period);
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), true);
    }
}
