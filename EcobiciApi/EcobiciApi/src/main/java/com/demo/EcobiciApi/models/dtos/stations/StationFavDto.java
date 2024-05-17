package com.demo.EcobiciApi.models.dtos.stations;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StationFavDto {

        private Long station_fav_id;
        private Long user_id;
        private String station_id;
        private String name;
        private String address;
        private String alias;

    }
