package com.backend.repo;

import com.backend.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NurseRepo extends JpaRepository<Nurse, Integer> {
    @Query(value = "SELECT * FROM nurse WHERE first_name = ?1 AND last_name = ?2 AND phone = ?3 AND email = ?4", nativeQuery = true)
    Nurse findNurseByDetails(String firstName, String lastName, String phone, String email);

    @Query(value = "SELECT * FROM nurse WHERE id = ?1", nativeQuery = true)
    Nurse findNurseById(int id);

    @Query(value = "SELECT * FROM nurse WHERE email = ?1", nativeQuery = true)
    Nurse findNurseByEmail(String email);
}
