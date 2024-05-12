package com.demo.EcobiciApi.services;

import com.demo.EcobiciApi.models.dtos.stations.Root;
import com.demo.EcobiciApi.models.dtos.stations.Station;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.services.api.EcobiciClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class EcobiciService {

    @Autowired
    EcobiciClient ecobiciClient;


    //create

    //get-all (read)

    //find-by-id (read)

    //update

    //delete

    //traer los atributos que yo quiero

    public List<StationAttribute> getStationAttributes(String clientId, String clientSecret) {
        List<StationAttribute> stationAttributes = new ArrayList<>();
        Root rootInfo = ecobiciClient.getStationInformation(clientId, clientSecret);
        Data dataInfo = rootInfo.getData();
        ArrayList<Station> stationsinfo = dataInfo.getStations();

        for (Station station : stationsinfo) {
            StationAttribute newStation = new StationAttribute();
            newStation.setStation_id(station.getStation_id());
            newStation.setName(station.getName());
            newStation.setAddress(station.getAddress());

            Root rootStatus = ecobiciClient.getStationStatus(clientId, clientSecret);
            Data dataStatus = rootStatus.getData();
            ArrayList<Station> stationsStatus = dataStatus.getStations();

            for (Station stationStatus : stationsStatus) {
                if (stationStatus.getStation_id().equals(newStation.getStation_id())) {
                    newStation.setNum_bikes_available(stationStatus.getNum_bikes_available());
                    newStation.setNum_docks_available(stationStatus.getNum_docks_available());
                    newStation.setStatus(stationStatus.getStatus());
                    break;
                }
            }
            stationAttributes.add(newStation);
        }
        return stationAttributes;
    }



}




