package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.model.Pins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinsRepository extends JpaRepository<Pins, Long> {
}
