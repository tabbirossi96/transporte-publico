package com.demo.EcobiciApi.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class StationFavorite {

    //propio de esta clase
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long station_fav_id;
    private String alias;
    //atributos a traer desde USER-API
    private Long user_id;

    //atributos a traer desde stationAttribute
    private String station_id;
    private String name;
    private String address;


    public StationFavorite(Long station_fav_id, Long user_id, String station_id, String name, String address,
                           String alias) {
    }
}
