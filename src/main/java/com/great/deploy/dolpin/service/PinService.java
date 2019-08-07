package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinInfo;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.model.Pins;

import java.util.List;

public interface PinService {
    List<PinResponse> getAllPins();

    Pins createPin(CreatePinRequest createPinRequest, String imageUrl);

    //  public Page<ClosePinResponse> getClosePins(Double latitude, Double latitude, Pageable pageable);
    PinDetailResponse getPinDetail(Long pinId);

    PinResponse updatePin(Long pinId, PinInfo pinInfo);

    void deletePin(Long pinId);

}
