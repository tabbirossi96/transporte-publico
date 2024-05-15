package com.demo.EcobiciApi.repositories;

import com.demo.EcobiciApi.models.entities.StationFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StationFavoriteRepository extends JpaRepository<StationFavorite, Long> {

    List<StationFavorite> findByUserId(Long user_id);

}
