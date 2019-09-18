package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.DolpinRequest;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.repository.HistoryRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PinsRepository pinsRepository;

    @Autowired
    VisitRepository visitRepository;

    @Override
    public Visit proof(ProofRequest proofRequest) {
        Integer accountId = proofRequest.getAccountId();
        Long pinId = proofRequest.getPinId();

        return visitRepository.save(Visit.of(pinId, accountId));
    }

    @Override
    public ProofResponse dolpin(DolpinRequest dolpinRequest) {
        return null;
    }


}
