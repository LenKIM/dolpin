package com.great.deploy.dolpin.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinRequest;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
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
    public List<PinResponse> getAllPins() {
        List<Pins> allPins = pinsRepository.findAll();
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
                                pins.getEndDate())
                ).collect(Collectors.toList());
    }

    @Override
    public PinDetailResponse getPinDetail(Long pinId) {

        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't found pinId"));

        return new PinDetailResponse(pin.getId(),
                pin.getTitle(),
                pin.getImgUrl(),
                pin.getImgProvider(),
                pin.getStartDate(),
                pin.getEndDate());
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
}
