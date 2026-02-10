package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class PatientDto {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String address;
    private String phone;
    private String gender;
    private String password;
    private NurseDto nurse;
    private AttendantDto attendant;
    private List<Integer> doctorsIDs;
}
