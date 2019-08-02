package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.ClosePinResponse;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.CreatePinResponse;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinInfo;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.model.Pins;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PinService {
  public List<PinResponse> getAllPins();
  public Pins createPin(CreatePinRequest createPinRequest, String imageUrl);
//  public Page<ClosePinResponse> getClosePins(Double latitude, Double latitude, Pageable pageable);
  public PinDetailResponse getPinDetail(Long pinId);
//  public PinInfo modifyPin(Long pinId, PinInfo pinInfo);
  public void deletePin(Long pinId);

}
