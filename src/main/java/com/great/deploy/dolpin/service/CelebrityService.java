package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.model.CelebrityMember;
import java.util.List;

public interface CelebrityService {
  public List<CelebrityResponse> getCelebrities();
}
