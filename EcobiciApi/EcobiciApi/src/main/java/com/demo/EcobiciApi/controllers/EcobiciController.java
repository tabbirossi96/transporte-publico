package com.demo.EcobiciApi.controllers;

import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.services.EcobiciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping ("/stations")

public class EcobiciController {

    @Autowired
    EcobiciService ecobiciService;

    //devuelva todas las estaciones existentes
    @GetMapping("/all-station")
    public List<StationAttribute> getStationAttributes(@RequestParam String clientId, @RequestParam String clientSecret){
        return ecobiciService.getStationAttributes(clientId, clientSecret);
    }


    //guardar estacion favorita
    //request:id_estacion_fav, nombre usuario
    //llenar tu entity de la estasciones favorita y listo
    //response: 200 ok




    //levantar las estaciones favortias POR Usuario

}
