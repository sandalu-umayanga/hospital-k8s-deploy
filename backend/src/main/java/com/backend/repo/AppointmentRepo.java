package com.backend.repo;

import com.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT * FROM appointment WHERE patient_id = ?1", nativeQuery = true)
    List<Appointment> findByPatientId(int patientId);

    @Query(value = "SELECT * FROM appointment WHERE doctor_id = ?1", nativeQuery = true)
    List<Appointment> findByDoctorId(int doctorId);
}
