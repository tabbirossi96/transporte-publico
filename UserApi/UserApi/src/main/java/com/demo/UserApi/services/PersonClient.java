package com.demo.UserApi.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//se conecta con el microservicio PersonApi con Eureka
@FeignClient(name = "person-services", path = "/persons")

public interface PersonClient {

    // llamo a los servicios que quiero usar de PersonApi

    @GetMapping("/find-id-by-DNI/{dni}") //busca el personid a traves de su DNI
    Long findIdByDNI(@PathVariable int dni);
}



