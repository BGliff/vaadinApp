package com.example.vaadinapp.repo;

import com.example.vaadinapp.domain.Mielogram;
import com.example.vaadinapp.domain.PatientResearches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientResearchesRepo extends JpaRepository<PatientResearches, Long> {
    @Query("select new com.example.vaadinapp.domain.Mielogram(p.type, count(p.id))  from PatientResearches p group by p.type")
    List<Mielogram> countPercent();
}
