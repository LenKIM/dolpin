package com.great.deploy.dolpin.service;

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

import java.util.ArrayList;
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
                    .orElseThrow(() -> new ResourceNotFoundException("Not found memberId => memberId"));
        }

        if (StringUtils.isEmpty(groupId)) {
            celebrityGroup = celebrityGroupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found memberId => memberId"));
        }
        return pinsRepository.save(new Pins(pins, imageUrl, celebrityMember, celebrityGroup));
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
    public List<PinResponse> getPinsByListCelebritiesId(List<Long> celebritiesList, Integer accountId) {
        ArrayList<Pins> pinsArrayList = new ArrayList<>();
        celebritiesList.forEach(
                memberId -> pinsArrayList.addAll(pinsRepository.findByCelebrityMember_Id(memberId))
        );
        return getPinResponses(accountId, pinsArrayList);
    }

    @Override
    public PinResponse getPinDetail(Long pinId, Integer accountId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId => " + pinId));
        List<Long> visits = visitRepository.findAllByAccountId(accountId).stream().map(Visit::getPinId).collect(Collectors.toList());
        Long pinCount = visitRepository.countVisitByPinId(pinId).orElse(0L);
        return new PinResponse(
                pin,
                visits.stream().anyMatch(visit -> visit.equals(pin.getId())),
                pinCount
        );
    }

    @Override
    public Pins modifyPin(Long pinId, PinRequest pinRequest) {
        return pinsRepository.findById(pinId)
                .map(pin -> {
                            pin.setDate(pinRequest);
                            return pinsRepository.save(pin);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("pinId =>" + pinId));
    }

    @Override
    public void deletePin(Long pinId) {
        Pins pin = pinsRepository.findById(pinId)
                .orElseThrow(() -> new ResourceNotFoundException("pinId =>" + pinId));
        pinsRepository.delete(pin);
    }

    private List<PinResponse> getPinResponses(Integer accountId, List<Pins> allPins) {
        List<Long> visits = visitRepository.findAllByAccountId(accountId).stream().map(Visit::getPinId).collect(Collectors.toList());

        return allPins.stream()
                .map(pins -> {
                            Long visitCount = visitRepository.countVisitByPinId(pins.getId()).orElse(0L);
                            return new PinResponse(
                                    pins,
                                    visits.stream().anyMatch(visit -> visit.equals(pins.getId())),
                                    visitCount
                            );
                        }
                ).collect(Collectors.toList());
    }
}
