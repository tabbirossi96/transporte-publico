package com.demo.EcobiciApi.models.dtos.stations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class NumBikesAvailableTypes{
    private int mechanical;
    private int ebike;
}
