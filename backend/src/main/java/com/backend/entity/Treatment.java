package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "treatment")

@IdClass(TreatmentID.class)
public class Treatment {

    @Id
    private int patientId;

    @Id
    private int doctorId;

    private String observations;
    private String treatments;

    @ManyToOne
    @JoinColumn(name = "patientId", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId", insertable = false, updatable = false)
    private Doctor doctor;
}
