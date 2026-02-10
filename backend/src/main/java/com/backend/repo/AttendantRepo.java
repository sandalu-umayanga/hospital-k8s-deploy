package com.backend.repo;

import com.backend.entity.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttendantRepo extends JpaRepository<Attendant, Integer> {
    @Query(value = "SELECT * FROM attendant WHERE first_name = ?1 AND last_name = ?2 AND phone = ?3 AND email = ?4", nativeQuery = true)
    Attendant findAttendantByDetails(String firstName, String lastName, String phone, String email);

    @Query(value = "SELECT * FROM attendant WHERE id = ?1", nativeQuery = true)
    Attendant findAttendantById(int id);

    @Query(value = "SELECT * FROM attendant WHERE email = ?1", nativeQuery = true)
    Attendant findAttendantByEmail(String email);
}
