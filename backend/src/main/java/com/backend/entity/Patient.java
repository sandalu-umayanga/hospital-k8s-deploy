package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "patient")

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "nurse_id", referencedColumnName = "id")
    private Nurse nurse;

    @ManyToOne
    @JoinColumn(name = "attendant_id", referencedColumnName = "id")
    private Attendant attendant;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "patient_doctor",
            joinColumns = {
            @JoinColumn(name = "patient_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "id")
            })
    private List<Doctor> doctors;

}
