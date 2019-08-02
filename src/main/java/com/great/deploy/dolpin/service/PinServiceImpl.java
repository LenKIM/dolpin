package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.ClosePinResponse;
import com.great.deploy.dolpin.dto.PinDetailResponse;
import com.great.deploy.dolpin.dto.PinInfo;
import com.great.deploy.dolpin.dto.PinResponse;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.PinsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PinServiceImpl implements PinService {

  private PinsRepository pinsRepository;

  public PinServiceImpl(PinsRepository pinsRepository) {
    this.pinsRepository = pinsRepository;
  }

  @Override
  public List<PinResponse> getAllPins() {
    List<Pins> allPins = pinsRepository.findAll();
    List<PinResponse> responses = new ArrayList<>();

    for (int i = 0; i < allPins.size(); i++) {
      responses.add(
          PinResponse.builder()
              .id(allPins.get(i).getId())
              .latitude(allPins.get(i).getLatitude())
              .longitude(allPins.get(i).getLongitude())
              .build());
    }
    return responses;
  }

//  @Override
//  public Page<ClosePinResponse> getClosePins(Double latitude, Double latitude2, Pageable pageable) {
//    pinsRepository.
//    return null;
//  }


  @Override
  public PinDetailResponse getPinDetail(Long pinId) {
    Pins pin = pinsRepository.findById(pinId).orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));
    return PinDetailResponse.builder()
        .id(pin.getId())
        .title(pin.getTitle())
        .imgUrl(pin.getImgUrl())
        .imgProvider(pin.getImgUrl())
        .startDate(pin.getStartDate())
        .endDate(pin.getEndDate())
        .build();
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
    Pins pin = pinsRepository.findById(pinId).orElseThrow(() -> new ResourceNotFoundException("pinId", "id", "long"));
    pinsRepository.delete(pin);
  }
}
