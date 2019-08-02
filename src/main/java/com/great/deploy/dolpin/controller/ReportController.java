package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController  {

    ReportService reportService;

    @GetMapping("/proof/{pinId}")
    public Response<ProofResponse> reportProof(@PathVariable Long pinId) {
         ProofResponse proofResponse = reportService.proof(new ProofRequest(pinId));
        if(!proofResponse.isResult()){
            throw new ResourceNotFoundException("pins", "id", "id");
        }
        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proofResponse);
    }

//    @PostMapping("/proopf/dolpin")
//    public Response<ProofResponse> reportDolpin(@RequestBody DolpinRequest dolpinRequest){
//        reportService.dolpin(dolpinRequest);
//
//    }

}
