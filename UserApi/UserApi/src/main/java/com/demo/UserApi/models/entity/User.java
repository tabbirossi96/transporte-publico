package com.demo.UserApi.models.entity;

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
    private Long personId;


    public User(String userName, String password, Long personId){
        this.userName = userName;
        this.password = password;
        this.personId = personId;
    }

}
