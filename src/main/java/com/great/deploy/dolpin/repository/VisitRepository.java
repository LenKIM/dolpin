package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByAccountId(Integer id);
    Optional<Long> countVisitByPinId(long pinId);
}
