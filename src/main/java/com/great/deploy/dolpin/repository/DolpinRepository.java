package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Dolpin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DolpinRepository extends JpaRepository<Dolpin, Long> {

}
