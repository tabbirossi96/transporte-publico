package com.demo.EcobiciApi.models.dtos.stations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Root{
    private int last_updated;
    private int ttl;
    private Data data;
}
