package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.service.ReportService;
import com.great.deploy.dolpin.swagger.ProofResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ReportController", description = "Report 관련 API")
@RestController
@RequestMapping("/api/report")
public class ReportController  {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "아이돌 저장하기", response = ProofResponseModel.class)
    @GetMapping("/proof/{pinId}")
    public Response<ProofResponse> reportProof(@PathVariable Long pinId) {
        ProofResponse proofResponse = reportService.proof(new ProofRequest(pinId));
        if(!proofResponse.isResult()){
            throw new ResourceNotFoundException("pins", "id", "id");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proofResponse);
    }


}
