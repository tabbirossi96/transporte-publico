package com.demo.EcobiciApi.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor

public class Data{
    public ArrayList<Station> stations;
}
