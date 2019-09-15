package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinRequest;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.domain.Pins;

import java.util.List;

public interface PinService {

    List<PinResponse> getAllPins();

    List<PinResponse> getMemberPins(Long memberId);

    List<PinResponse> getGroupPins(Long groupId);

    Pins createPin(Pins pins, String imageUrl);

    PinDetailResponse getPinDetail(Long pinId);

    void deletePin(Long pinId);

    Pins modifyPin(Long pinId, PinRequest pinRequest);
}
