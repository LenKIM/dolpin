package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Pins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PinsRepository extends JpaRepository<Pins, Long> {

  List<Pins> findPinsByCelebrityGroup_IdAndCelebrityMember_Id(Long groupId, Long memberId);
  List<Pins> findPinsByCelebrityGroup_IdInAndCelebrityMember_IdIn(List<Long> groupId, List<Long> memberId);
  List<Pins> findByCelebrityMember_IdIn(Set<Long> id);

  List<Pins> findByCelebrityGroup_Id(Long celebrityGroupId);

  List<Pins> findByCelebrityMember_Id(Long celebrityMemberId);
}
