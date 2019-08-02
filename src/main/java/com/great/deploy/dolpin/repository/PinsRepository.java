package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.model.Pins;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinsRepository extends JpaRepository<Pins, Long> {

  List<Pins> findPinsByCelebrityGroup_IdAndCelebrityMember_Id(Long groupId, Long memberId);
  List<Pins> findPinsByCelebrityGroup_IdInAndCelebrityMember_IdIn(List<Long> groupId, List<Long> memberId);
  List<Pins> findByCelebrityMember_IdIn(Set<Long> id);
}
