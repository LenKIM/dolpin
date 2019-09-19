package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.dto.PinRequest;
import com.great.deploy.dolpin.dto.PinResponse;

import java.util.List;

public interface PinService {

    List<PinResponse> getAllPins(Integer id);

    List<PinResponse> getMemberPins(Long memberId, Integer accountId);

    Pins createPin(Pins pins, String imageUrl);

    List<PinResponse> getGroupPins(Long groupId, Integer accountId);

    void deletePin(Long pinId);

    PinResponse getPinDetail(Long pinId, Integer accountId);

    Pins modifyPin(Long pinId, PinRequest pinRequest);
}
