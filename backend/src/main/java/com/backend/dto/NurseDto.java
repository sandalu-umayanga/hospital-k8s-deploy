package com.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class NurseDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String address;
    private String phone;
    private String gender;
    private String simpleOverview;
    private String password;
    private byte[] profilePicture;
    private List<PatientDto> patients;
}
