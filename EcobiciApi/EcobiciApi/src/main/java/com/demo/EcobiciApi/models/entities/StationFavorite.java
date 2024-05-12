package com.demo.EcobiciApi.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class StationFavorite {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long station_fav_id;
    private Long station_id;
    private Long user_id;
    private String name_station;
    private String address_station;
    // TODO: Agregar capacidad total y cantidad de bicis disponibles




}
