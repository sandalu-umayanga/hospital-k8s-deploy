/*package com.co200Project.backend;

import com.co200Project.backend.controller.PatientController;
import com.co200Project.backend.dto.PatientDto;
import com.co200Project.backend.dto.ResponseDto;
import com.co200Project.backend.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private ResponseDto responseDto;

    @InjectMocks
    private PatientController patientController;

    @Test
    void savePatient_shouldReturnAcceptedStatus() {
        PatientDto patientDto = new PatientDto(0, "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890", "Male", null, null, null);

        when(patientService.savePatient(patientDto)).thenReturn("01");
        when(responseDto.getCode()).thenReturn("01");
        when(responseDto.getMessage()).thenReturn("Succeed the process!");
        when(responseDto.getData()).thenReturn(patientDto);

        // Use lenient to ignore unnecessary stubbing warnings
        lenient().when(responseDto.getCode()).thenReturn("01");
        lenient().when(responseDto.getMessage()).thenReturn("Succeed the process!");
        lenient().when(responseDto.getData()).thenReturn(patientDto);

        // Add detailed logging or debugging
        System.out.println("Saving patient: " + patientDto);

        ResponseEntity<ResponseDto> response = patientController.savePatient(patientDto);

        // Print the response for debugging purposes
        System.out.println("Response: " + response);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void updatePatient_shouldReturnAcceptedStatus() {
        PatientDto patientDto = new PatientDto(1, "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890", "Male", null, null, null);

        when(patientService.updatePatient(patientDto)).thenReturn("02");
        when(responseDto.getCode()).thenReturn("02");
        when(responseDto.getMessage()).thenReturn("Patient updated successfully");
        when(responseDto.getData()).thenReturn(patientDto);

        // Use lenient to ignore unnecessary stubbing warnings
        lenient().when(responseDto.getCode()).thenReturn("01");
        lenient().when(responseDto.getMessage()).thenReturn("Succeed the process!");
        lenient().when(responseDto.getData()).thenReturn(patientDto);

        ResponseEntity<ResponseDto> response = patientController.updatePatient(patientDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void deletePatient_shouldReturnAcceptedStatus() {
        PatientDto patientDto = new PatientDto(1, "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890", "Male", null, null, null);

        when(patientService.deletePatient(patientDto)).thenReturn("04");
        when(responseDto.getCode()).thenReturn("04");
        when(responseDto.getMessage()).thenReturn("Patient deleted successfully");
        when(responseDto.getData()).thenReturn(patientDto);

        // Use lenient to ignore unnecessary stubbing warnings
        lenient().when(responseDto.getCode()).thenReturn("01");
        lenient().when(responseDto.getMessage()).thenReturn("Succeed the process!");
        lenient().when(responseDto.getData()).thenReturn(patientDto);

        ResponseEntity<ResponseDto> response = patientController.deletePatient(patientDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void findPatientById_shouldReturnAcceptedStatus() {
        int id = 1;
        PatientDto patientDto = new PatientDto(1, "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890", "Male", null, null, null);

        when(patientService.findPatientById(id)).thenReturn(patientDto);
        when(responseDto.getCode()).thenReturn("06");
        when(responseDto.getMessage()).thenReturn("Patient found");
        when(responseDto.getData()).thenReturn(patientDto);

        // Use lenient to ignore unnecessary stubbing warnings
        lenient().when(responseDto.getCode()).thenReturn("01");
        lenient().when(responseDto.getMessage()).thenReturn("Succeed the process!");
        lenient().when(responseDto.getData()).thenReturn(patientDto);

        ResponseEntity<ResponseDto> response = patientController.findPatientById(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}
*/