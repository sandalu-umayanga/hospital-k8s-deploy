package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto {

    private int patientId;
    private int doctorId;
    private String observations;
    private String treatments;
}
