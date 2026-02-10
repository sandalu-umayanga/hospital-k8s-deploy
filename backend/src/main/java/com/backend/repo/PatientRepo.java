package com.backend.repo;


import com.backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface PatientRepo extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT * FROM patient WHERE first_name = ?1 AND last_name = ?2 AND dob = ?3 AND address = ?4 AND phone = ?5", nativeQuery = true)
    Patient findPatientByDetails(String firstName, String lastName, LocalDate dob, String address, String phone);

    @Query(value = "SELECT * FROM patient WHERE id = ?1", nativeQuery = true)
    Patient findPatientById(int id);

    @Query(value = "SELECT * FROM patient WHERE first_name LIKE %?1% OR last_name LIKE %?1%", nativeQuery = true)
    List<Patient> findPatientByName(String name);

    @Query(value = "SELECT * FROM patient WHERE email = ?1", nativeQuery = true)
    Patient findPatientByEmail(String email);
}
