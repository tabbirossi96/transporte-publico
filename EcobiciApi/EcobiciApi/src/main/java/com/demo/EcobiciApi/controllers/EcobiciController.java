package com.demo.EcobiciApi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class EcobiciController {

    //devuelva todas las estaciones existentes
    @GetMapping("/all-station") //trae todas las personas
    public List<stationdto> allStation(){
        return userService.allPersons();
    }


    //guardar estacion favorita

    //levantar las estaciones favortias

}
