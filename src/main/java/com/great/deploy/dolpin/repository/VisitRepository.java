package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByAccountId(Integer id);
}
