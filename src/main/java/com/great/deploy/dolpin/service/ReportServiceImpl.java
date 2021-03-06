package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Dolpin;
import com.great.deploy.dolpin.domain.Report;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.dto.model.DolpinType;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;
import com.great.deploy.dolpin.repository.DolpinRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.repository.ReportRepository;
import com.great.deploy.dolpin.repository.VisitRepository;
import com.great.deploy.dolpin.service.s3.AmazonS3ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    DolpinRepository dolpinRepository;

    @Autowired
    PinsRepository pinsRepository;

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    AmazonS3ClientService amazonS3ClientService;

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
    public Dolpin dolpin(Long celebrityMemberId, PostedAddress address, PositingPeriod period, MultipartFile image, DolpinType dolpinType) {
        String imageUrl = amazonS3ClientService.uploadFileToS3Bucket(image, true);
        Dolpin dolpin = new Dolpin(celebrityMemberId, address, period, imageUrl, dolpinType);
        return dolpinRepository.save(dolpin);
    }

    @Override
    public Boolean addNewCelebrity(String groupName, String memberNames) {
        if ("".equals(groupName) || "".equals(memberNames) || groupName == null || memberNames == null) {
            return false;
        }
        reportRepository.save(new Report(groupName, memberNames));
        return true;
    }
}
