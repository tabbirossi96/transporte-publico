package com.demo.EcobiciApi.services.api;

import com.demo.EcobiciApi.models.dtos.stations.otros.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//se conecta con el microservicio PersonApi con Eureka
@FeignClient(name = "user-services", path = "/users")
public interface UserClient {

    // llamo a los servicios que quiero usar de USER-API

    @GetMapping("/all-user") //trae todos los users
    public List<User> allUser();

    @GetMapping("/find-user/{id}") // busca user por id
    public User findUser(@PathVariable Long id);

}
