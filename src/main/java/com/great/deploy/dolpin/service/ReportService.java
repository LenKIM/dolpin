package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Dolpin;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.model.Celebrity;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;

public interface ReportService {

    Visit proof(ProofRequest proofRequest);

    Dolpin dolpin(Celebrity celebrity, PostedAddress address, PositingPeriod period);
}
