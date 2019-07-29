package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;

public interface ReportService {

    ProofResponse proof(ProofRequest proofRequest);
}
