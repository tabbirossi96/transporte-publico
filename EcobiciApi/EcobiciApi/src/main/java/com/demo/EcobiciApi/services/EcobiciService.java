package com.demo.EcobiciApi.services;

import com.demo.EcobiciApi.models.dtos.stations.Root;
import com.demo.EcobiciApi.models.dtos.stations.Station;
import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.dtos.stations.otros.User;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import com.demo.EcobiciApi.services.api.EcobiciClient;
import com.demo.EcobiciApi.models.dtos.stations.Data;
import com.demo.EcobiciApi.services.api.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class EcobiciService {

    @Autowired
    EcobiciClient ecobiciClient;
    @Autowired
    UserClient userClient;

    //create

    public void createStationFav (Long station_id, String nameUser){
        StationFavDto newStationFavDto = new StationFavDto();
        User userL = userClient.findUser(newStationFavDto.getUser_id());
        this.getStationAttributes(String clientId, String clientSecret);

        newStationFavDto.setUser_id();
    }

    //en user crear un endpoint(metodo servicio)


    public void createUser(User user){
        //Creo una nueva instancia llamada newUser. Esta instancia representa al usuario que deseo crear
        User newUser = new User();
        // Llama al PersonClient(que se conecta por feing con PersonApi) para obtener el PersonDto completo
        PersonDto person = personClient.findPerson(user.getPersonId());

        // Defino los valores de los atributos de mi nuevo User. esto ingresa por body
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setStatus(user.getStatus());
        // Asigno el Id del PersonDto al atributo personId de mi nuevo User
        newUser.setPersonId(person.getId());
        // Guardo el nuevo objeto de la clase User en mi user-bd
        userRepository.save(newUser);
    }


    //get-all (read)
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

        //variable para usar de indice
        int i = 0;
        for ( StationAttribute aux : stationAttributes) {
            for (Station stationStatus : stationsStatus) {
                //agarro el id de mi aux y busco en stationstatus el mismo id
                if (stationStatus.getStation_id().equals(aux.getStation_id())) {
                    aux.setNum_bikes_available(stationStatus.getNum_bikes_available());
                    aux.setNum_docks_available(stationStatus.getNum_docks_available());
                    aux.setStatus(stationStatus.getStatus());
                    stationAttributes.set(i, aux);
                    break;
                }
            }
            //me muevo una posicion en el indice por cada vuelta
            i++;
        }
        //devuelvo la lista de estaciones con los atributos que yo necesito.
        return stationAttributes;
    }

    //find-by-userId List  (read)

    //update

    //delete





}




