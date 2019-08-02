package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.DolpinRequest;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.repository.HistoryRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PinsRepository pinsRepository;


    @Override
    public ProofResponse proof(ProofRequest proofRequest) {
//        pinsRepository.findById(proofRequest.getId())
//        .map(pins -> {historyRepository.save(new History())});
        return null;
    }

    @Override
    public ProofResponse dolpin(DolpinRequest dolpinRequest) {
        return null;
    }


}
