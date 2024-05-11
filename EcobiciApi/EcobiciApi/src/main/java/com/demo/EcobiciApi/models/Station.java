package com.demo.EcobiciApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Station{
    public String station_id;
    public String name;
    public String physical_configuration;
    public double lat;
    public double lon;
    public int altitude;
    public String address;
    public String post_code;
    public int capacity;
    public boolean is_charging_station;
    public ArrayList<String> rental_methods;
    public ArrayList<String> groups;
    public String obcn;
    public String short_name;
    public double nearby_distance;
    public boolean _ride_code_support;
    public RentalUris rental_uris;
    public String cross_street;
}
