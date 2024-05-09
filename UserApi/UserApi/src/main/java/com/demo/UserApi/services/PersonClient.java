package com.demo.UserApi.services;

import com.demo.UserApi.models.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//se conecta con el microservicio PersonApi con Eureka
@FeignClient(name = "person-services", path = "/persons")

public interface PersonClient {

    // llamo a los servicios que quiero usar de PersonApi

    @GetMapping("/all-persons")
    List<PersonDto> allPersons();

    @GetMapping("/find-person/{id}")
    PersonDto findPerson(@PathVariable Long id);
}



