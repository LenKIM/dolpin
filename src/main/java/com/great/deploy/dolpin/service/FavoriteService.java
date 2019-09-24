package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    public boolean isValidateMembers(List<Long> memberList){
        return memberList.stream().allMatch(member -> celebrityMemberRepository.existsById(member));
    }

    public boolean isValidateGroups(List<Long> groupList){
        return groupList.stream().allMatch((group -> celebrityMemberRepository.existsById(group)));
    }

}
