package com.demo.EcobiciApi.services.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//se conecta con el microservicio PersonApi con Eureka
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    // llamo a los servicios que quiero usar de USER-API

    @GetMapping("/find-id-by-username/{username}")
    Long findIdByUsername(@PathVariable String username);


}
