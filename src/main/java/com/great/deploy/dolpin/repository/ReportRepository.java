package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
