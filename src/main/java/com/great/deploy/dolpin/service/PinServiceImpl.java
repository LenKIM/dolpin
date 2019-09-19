package com.great.deploy.dolpin.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.domain.Visit;
import com.great.deploy.dolpin.dto.PinRequest;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PinServiceImpl implements PinService {

    @Autowired
    private PinsRepository pinsRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private CelebrityMemberRepository celebrityMemberRepository;
    @Autowired
    private CelebrityGroupRepository celebrityGroupRepository;

    @Override
    public Pins createPin(Pins pins, String imageUrl) {

        Long memberId = pins.getCelebrityMemberId();
        Long groupId = pins.getCelebrityGroupId();

        CelebrityMember celebrityMember = pins.getCelebrityMember();
        CelebrityGroup celebrityGroup = pins.getCelebrityGroup();

        if (StringUtils.isEmpty(memberId)) {
            celebrityMember = celebrityMemberRepository.findById(memberId)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't found memberId"));
        }

        if (StringUtils.isEmpty(groupId)) {
            celebrityGroup = celebrityGroupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't found groupId"));
        }
        return pinsRepository.save(
                new Pins(
                        pins.getLatitude(),
                        pins.getLongitude(),
                        pins.getTitle(),
                        imageUrl,
                        pins.getImgProvider(),
                        pins.getStartDate(),
                        pins.getEndDate(),
                        celebrityMember,
                        celebrityGroup)
        );
    }

    @Override
    public List<PinResponse> getAllPins(Integer accountId) {
        List<Pins> allPins = pinsRepository.findAll();
        return getPinResponses(accountId, allPins);
    }

    @Override
    public List<PinResponse> getMemberPins(Long memberId, Integer accountId) {
        List<Pins> allByCelebrityMemberId = pinsRepository.findByCelebrityMember_Id(memberId);
        return getPinResponses(accountId, allByCelebrityMemberId);
    }

    @Override
    public List<PinResponse> getGroupPins(Long groupId, Integer accountId) {
        List<Pins> allByCelebrityMemberId = pinsRepository.findByCelebrityGroup_Id(groupId);
        return getPinResponses(accountId, allByCelebrityMemberId);
    }

    @Override
    public PinResponse getPinDetail(Long pinId, Integer accountId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't found pinId"));

        List<Long> visits = visitRepository.findAllByAccountId(accountId).stream().map(Visit::getPinId).collect(Collectors.toList());

        return new PinResponse(pin.getId(),
                pin.getTitle(),
                pin.getLatitude(),
                pin.getLongitude(),
                pin.getImgProvider(),
                pin.getImgUrl(),
                pin.getStartDate(),
                pin.getEndDate(),
                pin.getCelebrityMemberId(),
                pin.getCelebrityGroupId(),
                pin.getComments(),
                visits.stream().anyMatch(a -> a.equals(pin.getId()))
        );
    }

    @Override
    public Pins modifyPin(Long pinId, PinRequest pinRequest) {
        return pinsRepository.findById(pinId)
                .map(pin -> {
                            pin.setTitle(pinRequest.getTitle());
                            pin.setImgUrl(pinRequest.getImgUrl());
                            pin.setImgProvider(pinRequest.getImgProvider());
                            pin.setLatitude(pinRequest.getLatitude());
                            pin.setLongitude(pinRequest.getLongitude());
                            pin.setStartDate(pinRequest.getStartDate());
                            pin.setEndDate(pinRequest.getEndDate());
                            return pinsRepository.save(pin);
                        }
                ).orElseThrow(() -> new NotFoundException("Not Fount pin ID " + pinId));
    }

    @Override
    public void deletePin(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't found pinId"));
        pinsRepository.delete(pin);
    }

    private List<PinResponse> getPinResponses(Integer accountId, List<Pins> allPins) {
        List<Long> visits = visitRepository.findAllByAccountId(accountId).stream().map(Visit::getPinId).collect(Collectors.toList());

        return allPins.stream()
                .map(pins ->
                        new PinResponse(
                                pins.getId(),
                                pins.getTitle(),
                                pins.getLatitude(),
                                pins.getLongitude(),
                                pins.getImgProvider(),
                                pins.getImgUrl(),
                                pins.getStartDate(),
                                pins.getEndDate(),
                                pins.getCelebrityMemberId(),
                                pins.getCelebrityGroupId(),
                                pins.getComments(),
                                visits.stream().anyMatch(a -> a.equals(pins.getId()))
                        )
                ).collect(Collectors.toList());
    }
}
