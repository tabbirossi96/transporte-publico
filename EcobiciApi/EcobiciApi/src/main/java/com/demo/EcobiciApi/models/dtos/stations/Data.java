package com.demo.EcobiciApi.models.dtos.stations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Data{
    private ArrayList<Station> stations;
}
