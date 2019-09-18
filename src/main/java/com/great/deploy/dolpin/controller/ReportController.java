package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.*;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.service.ReportService;
import com.great.deploy.dolpin.swagger.ProofResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "ReportController", description = "Report 관련 API")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Idol Support", response = ProofResponseModel.class)
    @GetMapping("/proof/{pinId}")
    public Response<Boolean> reportProof(
            @PathVariable Long pinId,
            @ApiIgnore @CurrentUser Account account) {

        Account.validateAccount(account);

        ProofRequest proofRequest = new ProofRequest(pinId, account.getId());
        reportService.proof(proofRequest);
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                true
        );
    }

    @ApiOperation(value = "아이돌 Pins 인증(Authentication)")
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
}
