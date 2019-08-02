package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.CelebrityGroup;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import org.springframework.stereotype.Service;

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

        if (createPinRequest.getMemberId() != null) {
            celebrityMember = celebrityMemberRepository.findById(createPinRequest.getMemberId())
                    .orElseThrow(() -> new ResourceNotFoundException("memberId", "id", "long"));
        }

        if (createPinRequest.getGroupId() != null) {
            celebrityGroup = celebrityGroupRepository.findById(createPinRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("groupId", "id", "long"));
        }

        Pins pins = new Pins.PinsBuilder()
                .latitude(createPinRequest.getLatitude())
                .longitude(createPinRequest.getLongitude())
                .title(createPinRequest.getTitle())
                .imgUrl(imageUrl)
                .imgProvider(createPinRequest.getImgProvider())
                .startDate(createPinRequest.getStartDate())
                .endDate(createPinRequest.getEndDate())
                .celebrityGroup(celebrityGroup)
                .celebrityMember(celebrityMember)
                .build();

        return pins;
//    pin.setLatitude(createPinRequest.getLatitude());
//    pin.setLongitude(createPinRequest.getLongitude());
//    pin.setTitle(createPinRequest.getTitle());
//    pin.setImgUrl(c);
//    pin.setCelebrityGroup(celebrityGroup);
//    pin.setCelebrityMember(celebrityMember);
//    pinsRepository.save()
//    return null;
    }

    @Override
    public List<PinResponse> getAllPins() {
        List<Pins> allPins = pinsRepository.findAll();
        return allPins.stream().map(
                pins -> new PinResponse(pins.getId(), pins.getLatitude(), pins.getLongitude())
        ).collect(Collectors.toList());
    }

    @Override
    public PinDetailResponse getPinDetail(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));

        return new PinDetailResponse(pin.getId(), pin.getTitle(), pin.getImgUrl(), pin.getImgProvider(), pin.getStartDate(), pin.getEndDate());
    }

//  @Override
//  public PinInfo modifyPin(Long pinId, PinInfo pinInfo) {
//    Pins pins = new Pins();
//    pins.setId(pinId);
//    pins.set
//
//    return null;
//  }


    @Override
    public void deletePin(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));
        pinsRepository.delete(pin);
    }
}
