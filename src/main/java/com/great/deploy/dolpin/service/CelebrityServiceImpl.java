package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.model.CelebrityGroup;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CelebrityServiceImpl implements CelebrityService {

  private CelebrityGroupRepository celebrityGroupRepository;
  private CelebrityMemberRepository celebrityMemberRepository;

  public CelebrityServiceImpl(
      CelebrityGroupRepository celebrityGroupRepository,
      CelebrityMemberRepository celebrityMemberRepository) {
    this.celebrityGroupRepository = celebrityGroupRepository;
    this.celebrityMemberRepository = celebrityMemberRepository;
  }

  @Override
  public List<CelebrityResponse> getCelebrities() {
    List<CelebrityGroup> allGroups = celebrityGroupRepository.findAll();
    List<CelebrityResponse> response = new ArrayList<>();

    for (int i = 0; i < allGroups.size(); i++) {
      List<CelebrityMember> groupIdMemeber = celebrityMemberRepository.findByCelebrityGroup_Id(allGroups.get(i).getId());
      for (int j = 0; j < groupIdMemeber.size(); j++) {
        CelebrityResponse celebrityResponse = new CelebrityResponse();
        celebrityResponse.setId(groupIdMemeber.get(j).getId());
        celebrityResponse.setName(groupIdMemeber.get(j).getName());
        celebrityResponse.setBirthday(groupIdMemeber.get(j).getBirthday());
        celebrityResponse.setPicUrl(groupIdMemeber.get(j).getPicUrl());
        celebrityResponse.setGroupId(allGroups.get(i).getId());
        celebrityResponse.setGroupName(allGroups.get(i).getName());

        response.add(celebrityResponse);
      }
    }

    return response;
  }
}
