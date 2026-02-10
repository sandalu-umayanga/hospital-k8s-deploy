package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "nurse")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "simpleOverview")
    private String simpleOverview;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "profile_picture" , columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    @OneToMany(mappedBy = "nurse")
    private List<Patient> patients;


}
