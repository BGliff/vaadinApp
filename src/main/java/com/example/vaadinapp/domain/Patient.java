package com.example.vaadinapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue
    private Long id;
    private String fio;
    private int age;
    private long medicalHistory;
    private Date date;
    private String department;
    private String diagnosis;
}
