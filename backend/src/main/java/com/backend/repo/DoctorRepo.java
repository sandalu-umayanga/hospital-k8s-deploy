package com.backend.repo;

import com.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    @Query(value = "SELECT * FROM doctor WHERE first_name = ?1 AND last_name = ?2 AND phone = ?3 AND email = ?4", nativeQuery = true)
    Doctor findDoctorByDetails(String firstName, String lastName, String phone , String email);

    @Query(value = "SELECT * FROM doctor WHERE id = ?1", nativeQuery = true)
    Doctor findDoctorById(int id);

    @Query(value = "SELECT * FROM doctor WHERE email = ?1", nativeQuery = true)
    Doctor findDoctorByEmail(String email);
}
