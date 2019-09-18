package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.DolpinRequest;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;

public interface ReportService {

    Visit proof(ProofRequest proofRequest);

    ProofResponse dolpin(DolpinRequest dolpinRequest);
}
