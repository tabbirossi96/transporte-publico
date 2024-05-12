package com.demo.EcobiciApi.models.dtos.stations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Station{

    //TODO: CAMBIAR TODOS LOS PUBLICS A PRIVATE
    private String station_id;
    private String name;
    private String physical_configuration;
    private double lat;
    private double lon;
    private int altitude;
    private String address;
    private String post_code;
    private int capacity;
    private boolean is_charging_station;
    private ArrayList<String> rental_methods;
    private ArrayList<String> groups;
    private String obcn;
    private String short_name;
    private double nearby_distance;
    private boolean _ride_code_support;
    private RentalUris rental_uris;
    private String cross_street;
//ELIMINAR LO REPETIDO
   // private String station_id;
    private int num_bikes_available;
    private NumBikesAvailableTypes num_bikes_available_types;
    private int num_bikes_disabled;
    private int num_docks_available;
    private int num_docks_disabled;
    private int last_reported;
    // private boolean is_charging_station;
    private String status;
    private int is_installed;
    private int is_renting;
    private int is_returning;
    private Object traffic;
}
