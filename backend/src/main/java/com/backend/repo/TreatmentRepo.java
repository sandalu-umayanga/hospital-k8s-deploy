package com.backend.repo;

import com.backend.entity.Treatment;
import com.backend.entity.TreatmentID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface TreatmentRepo extends JpaRepository<Treatment, TreatmentID> {
    @Query(value = "SELECT * FROM treatment WHERE patient_id = ?1", nativeQuery = true)
    List<Treatment> findByPatientId(int patientId);
}
