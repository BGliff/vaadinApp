package com.example.vaadinapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class PatientResearches {
    @Id
    @GeneratedValue
    private long id;
    private String type;
    private Date date;
}
