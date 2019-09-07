package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.model.CelebrityGroup;
import com.great.deploy.dolpin.model.CelebrityMember;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CelebrityServiceImpl implements CelebrityService {

    @Autowired
    private CelebrityGroupRepository celebrityGroupRepository;
    @Autowired
    private CelebrityMemberRepository celebrityMemberRepository;

    @Override
    public List<CelebrityResponse> getCelebrities() {
        List<CelebrityGroup> allGroups = celebrityGroupRepository.findAll();
        List<CelebrityResponse> response = new ArrayList<>();

        for (CelebrityGroup allGroup : allGroups) {
            List<CelebrityMember> groupIdMember = celebrityMemberRepository.findByCelebrityGroup_Id(allGroup.getId());
            for (CelebrityMember celebrityMember : groupIdMember) {
                CelebrityResponse celebrityResponse = new CelebrityResponse();
                celebrityResponse.setId(celebrityMember.getId());
                celebrityResponse.setName(celebrityMember.getName());
                celebrityResponse.setBirthday(celebrityMember.getBirthday());
                celebrityResponse.setPicUrl(celebrityMember.getPicUrl());
                celebrityResponse.setGroupId(allGroup.getId());
                celebrityResponse.setGroupName(allGroup.getName());

                response.add(celebrityResponse);
            }
        }

        return response;
    }
}
