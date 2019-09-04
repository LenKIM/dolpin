package com.great.deploy.dolpin.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinInfo;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.CelebrityGroup;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PinServiceImpl implements PinService {

    private PinsRepository pinsRepository;
    private CelebrityMemberRepository celebrityMemberRepository;
    private CelebrityGroupRepository celebrityGroupRepository;

    public PinServiceImpl(PinsRepository pinsRepository,
                          CelebrityMemberRepository celebrityMemberRepository,
                          CelebrityGroupRepository celebrityGroupRepository) {
        this.pinsRepository = pinsRepository;
        this.celebrityMemberRepository = celebrityMemberRepository;
        this.celebrityGroupRepository = celebrityGroupRepository;
    }

    @Override
    public Pins createPin(CreatePinRequest createPinRequest, String imageUrl) {

        CelebrityMember celebrityMember = null;
        CelebrityGroup celebrityGroup = null;

        if (StringUtils.isEmpty(createPinRequest.getMemberId())) {
            celebrityMember = celebrityMemberRepository.findById(createPinRequest.getMemberId())
                    .orElseThrow(() -> new ResourceNotFoundException("memberId", "id", "long"));
        }

        if (StringUtils.isEmpty(createPinRequest.getGroupId())) {
            celebrityGroup = celebrityGroupRepository.findById(createPinRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("groupId", "id", "long"));
        }

        return pinsRepository.save(new Pins(createPinRequest.getLatitude(), createPinRequest.getLongitude(),
                createPinRequest.getTitle(), imageUrl, createPinRequest.getImgProvider(),
                createPinRequest.getStartDate(), createPinRequest.getEndDate(), celebrityMember, celebrityGroup));
    }

    @Override
    public List<PinResponse> getAllPins() {
        List<Pins> allPins = pinsRepository.findAll();
        return allPins.stream()
                .map(pins ->
                        new PinResponse(pins.getId(), pins.getTitle(), pins.getLatitude(), pins.getLongitude(), pins.getImgProvider(), pins.getImgUrl(), pins.getStartDate(), pins.getEndDate())
                ).collect(Collectors.toList());
    }

    @Override
    public PinDetailResponse getPinDetail(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));

        return new PinDetailResponse(pin.getId(), pin.getTitle(), pin.getImgUrl(), pin.getImgProvider(), pin.getStartDate(), pin.getEndDate());
    }

    @Override
    public PinResponse updatePin(Long pinId, PinInfo pinInfo) {
        return pinsRepository.findById(pinId)
                .map(pin -> {
                            pin.setTitle(pinInfo.getTitle());
                            pin.setImgUrl(pinInfo.getImgUrl());
                            pin.setImgProvider(pinInfo.getImgProvider());
                            pin.setLatitude(pinInfo.getLatitude());
                            pin.setLongitude(pinInfo.getLongitude());
                            pin.setStartDate(pinInfo.getStartDate());
                            pin.setEndDate(pinInfo.getEndDate());
                            return pinsRepository.save(pin);
                        }
                )
                .map(pin -> new PinResponse(pin.getId(),
                        pin.getTitle(),
                        pin.getLatitude(),
                        pin.getLongitude(),
                        pin.getImgProvider(),
                        pin.getImgUrl(),
                        pin.getStartDate(),
                        pin.getEndDate()))
                .orElseThrow(() -> new NotFoundException("Not Fount pin ID " + pinId));
    }


    @Override
    public void deletePin(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));
        pinsRepository.delete(pin);
    }
}
