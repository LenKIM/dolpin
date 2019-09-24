package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Dolpin;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.model.Celebrity;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;
import com.great.deploy.dolpin.repository.DolpinRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    DolpinRepository dolpinRepository;

    @Autowired
    PinsRepository pinsRepository;

    @Autowired
    VisitRepository visitRepository;

    @Override
    public Visit proof(ProofRequest proofRequest) {
        Integer accountId = proofRequest.getAccountId();
        Long pinId = proofRequest.getPinId();

        if (!pinsRepository.existsById(pinId)) {
            return Visit.NOT_FOUND;
        } else {
            return visitRepository.save(Visit.of(pinId, accountId));
        }
    }

    @Override
    public Dolpin dolpin(Celebrity celebrity, PostedAddress address, PositingPeriod period) {
        Dolpin dolpin = new Dolpin(celebrity, address, period);
        return dolpinRepository.save(dolpin);
    }
}
