package com.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class AttendantDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String address;
    private String phone;
    private String gender;
    private String password;
}
