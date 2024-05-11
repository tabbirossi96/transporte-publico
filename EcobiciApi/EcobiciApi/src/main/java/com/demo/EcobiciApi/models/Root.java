package com.demo.EcobiciApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Root{
    public int last_updated;
    public int ttl;
    public Data data;
}
