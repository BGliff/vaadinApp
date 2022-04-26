package com.example.vaadinapp.repo;

import com.example.vaadinapp.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    @Query("from Patient p where p.fio like concat('%', :fio, '%') ")
    List<Patient> findByFio(@Param("fio") String fio);
}
