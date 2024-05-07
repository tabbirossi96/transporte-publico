package com.demo.PersonApi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private int dni;
    private String email;
    private LocalDate birthdate;

    public Person(String name, String lastname, int dni, String email, LocalDate birthdate){
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.birthdate = birthdate;
    }

}
