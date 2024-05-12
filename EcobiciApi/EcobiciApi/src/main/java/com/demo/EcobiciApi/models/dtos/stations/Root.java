package com.demo.EcobiciApi.models.dtos.stations;
import com.demo.EcobiciApi.models.dtos.stations.Data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Root{
    private int last_updated;
    private int ttl;
    private Data data;
}
