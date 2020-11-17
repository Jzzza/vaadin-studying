package ru.dmartyanov.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue
    Long id;
    String lastName;
    String firstName;
    String patronymic;
}
