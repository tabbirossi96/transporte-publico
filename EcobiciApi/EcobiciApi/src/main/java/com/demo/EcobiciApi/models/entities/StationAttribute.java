package com.demo.EcobiciApi.models.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StationAttribute {


    //StationInformation
    private String station_id;
    private String name;
    private String address;
    //StationStatus
    private int num_bikes_available;
    private int num_docks_available;
    private String status;

}
