package com.demo.EcobiciApi.repositories;

import com.demo.EcobiciApi.models.entities.StationFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StationFavoriteRepository extends JpaRepository<StationFavorite, Long> {

    @Query("SELECT s FROM StationFavorite s WHERE s.user_id = ?1")
    List<StationFavorite> findByUser_id(Long user_id);

}
