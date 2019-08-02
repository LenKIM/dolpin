package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
