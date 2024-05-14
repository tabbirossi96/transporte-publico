package com.demo.EcobiciApi.models.dtos.stations.otros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    private Long id;
    private String userName;
    private String password;
    private String status; //TODO: cambiar nombre de atributo a status_user
    private Long personId;
}
