package com.demo.EcobiciApi.services;

import com.demo.EcobiciApi.models.dtos.stations.Root;
import com.demo.EcobiciApi.models.dtos.stations.Station;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.services.api.EcobiciClient;
import com.demo.EcobiciApi.models.dtos.stations.Data;
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
        //creo una lista vacía que luego tendra los atributos que yo quiero
        List<StationAttribute> stationAttributes = new ArrayList<>();
        // Llama al ecobiciClient, que esta se conecta con la API externa de Ecobici,
        // para obtener los datos de station information
        Root rootInfo = ecobiciClient.getStationInformation(clientId, clientSecret);
        Data dataInfo = rootInfo.getData();
        //obtengo la lista de stations information
        ArrayList<Station> stationsinfo = dataInfo.getStations();

        //itero dentro de la lista de station information
        for (Station station : stationsinfo) {
            //para cada estacion de la lista creo un nuevo objeto
            StationAttribute newStation = new StationAttribute();
            //a cada nuevo objeto, establesco los atributos que necesito de station information
            newStation.setStation_id(station.getStation_id());
            newStation.setName(station.getName());
            newStation.setAddress(station.getAddress());
            //cuando recolecte todos los atributos en el objeto newStation, los guardo en un StationAttribures
            stationAttributes.add(newStation);
        }

        //ahora quiero llamar a station status de la API externa
        Root rootStatus = ecobiciClient.getStationStatus(clientId, clientSecret);
        Data dataStatus = rootStatus.getData();
        ArrayList<Station> stationsStatus = dataStatus.getStations();

        //inicio un segundo bucle para iterar en la lista de station status
        //para obtener los atributos que quiero de esa lista
        int i = 0;
        for ( StationAttribute aux : stationAttributes) {
            //con el if compruebo que los id de las estaciones de station status coincidan con los
            //id de las station information
            for (Station stationStatus : stationsStatus) {
                if (stationStatus.getStation_id().equals(aux.getStation_id())) {
                    aux.setNum_bikes_available(stationStatus.getNum_bikes_available());
                    aux.setNum_docks_available(stationStatus.getNum_docks_available());
                    aux.setStatus(stationStatus.getStatus());
                    stationAttributes.set(i, aux);
                    break;
                }
            }
            i++;
        }
                //devuelvo la lista de estaciones con los atributos que yo necesito.
        return stationAttributes;
    }



}




