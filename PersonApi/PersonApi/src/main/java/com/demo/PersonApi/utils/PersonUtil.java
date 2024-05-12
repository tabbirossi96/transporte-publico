package com.demo.PersonApi.utils;

import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component

public class PersonUtil {

    public PersonDto personMapper (Person person){
        return new PersonDto(person.getId(), person.getName(), person.getLastname(), person.getDni(),
                person.getEmail(), person.getBirthdate());
    }

    public List<PersonDto> personMapper(List<Person> persons){
        List<PersonDto> personDto = new ArrayList<>();
        for (Person person : persons) {
            personDto.add(personMapper(person));
        }
        return personDto;
        }
}
