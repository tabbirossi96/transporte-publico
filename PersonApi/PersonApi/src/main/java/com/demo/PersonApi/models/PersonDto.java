package com.demo.PersonApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonDto {

    private Long id;
    private String name;
    private String lastname;
    private int dni;
    private String email;
    private LocalDate birthdate;
}
