package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.HistoryRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PinsRepository pinsRepository;


    @Override
    public ProofResponse proof(ProofRequest proofRequest) {
        Optional<Pins> id = pinsRepository.findById(proofRequest.getId());
        Pins pins;
        if(id.isPresent()){
            pins = id.get();
        }
//        여기에 유저 저장하고.
//        historyRepository.save()
//        save한 값이 저장되고 난 이후에 persistent 상태의 값을 확인 후 result해줘야 한다.
//        return new ProofResponse();
        return null;
    }


}
