package com.demo.EcobiciApi.services.api;

import com.demo.EcobiciApi.models.dtos.stations.Root;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "ecobici-services", url = "https://apitransporte.buenosaires.gob.ar/ecobici/gbfs")
public interface EcobiciClient {

    @GetMapping("/stationInformation")
    Root getStationInformation(@RequestParam(name = "client_id") String clientId,
                               @RequestParam(name = "client_secret") String clientSecret);

    @GetMapping("/stationStatus")
    Root getStationStatus(@RequestParam(name = "client_id") String clientId,
                          @RequestParam(name = "client_secret") String clientSecret);

}
