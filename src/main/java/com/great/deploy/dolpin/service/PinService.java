package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.ClosePinResponse;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PinService {
  public List<PinResponse> getAllPins();
//  public Page<ClosePinResponse> getClosePins(Double latitude, Double latitude, Pageable pageable);
  public PinDetailResponse getPinDetail(Long pinId);
}
