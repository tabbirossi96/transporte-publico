package com.demo.EcobiciApi.models.dtos.stations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor

public class Data{
    private ArrayList<Station> stations;
}
