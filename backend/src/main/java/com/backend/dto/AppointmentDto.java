package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDto {
    private int id;
    private int patientId;
    private int doctorId;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateTime;
    private String status;
    private String reason;
}
