package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.model.CelebrityMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebrityMemberRepository extends JpaRepository<CelebrityMember, Long> {
  List<CelebrityMember> findByCelebrityGroup_Id(Long id);
}
