package com.demo.UserApi.models.entity;

import com.demo.UserApi.models.dto.PersonDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String status; //TODO: cambiar nombre de atributo a status_user
    private Long personId;


    public User(String userName, String password, String status, Long personId){
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.personId = personId;
    }

}
