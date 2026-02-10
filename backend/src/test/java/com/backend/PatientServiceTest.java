package com.backend;

import com.backend.dto.PatientDto;
import com.backend.entity.Patient;
import com.backend.messages.PatientMessages;
import com.backend.repo.PatientRepo;
import com.backend.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepo patientRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private PatientDto patientDto;
    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientDto = new PatientDto(1, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-5555", "Male", "password", null, null, null);
        patient = new Patient(1, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-5555", "Male", "password", null, null, null);
    }

    @Test
    void testSavePatient_PatientAlreadyExists() {
        when(patientRepo.findPatientByDetails(any(), any(), any(), any(), any())).thenReturn(patient);

        String result = patientService.savePatient(patientDto);

        assertEquals(PatientMessages.PATIENT_ALREADY_EXISTS, result);
    }

    @Test
    void testSavePatient_Success() {
        when(patientRepo.findPatientByDetails(any(), any(), any(), any(), any())).thenReturn(null);
        when(modelMapper.map(any(PatientDto.class), any())).thenReturn(patient);
        when(patientRepo.save(any(Patient.class))).thenReturn(patient);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String result = patientService.savePatient(patientDto);

        assertEquals(PatientMessages.PATIENT_SAVED, result);
    }

    @Test
    void testFindPatientById_PatientFound() {
        when(patientRepo.findById(1)).thenReturn(Optional.of(patient));
        when(modelMapper.map(any(Patient.class), any())).thenReturn(patientDto);

        PatientDto foundPatient = patientService.findPatientById(1);

        assertEquals(patientDto, foundPatient);
    }

    @Test
    void testFindPatientById_PatientNotFound() {
        when(patientRepo.findById(1)).thenReturn(Optional.empty());

        PatientDto foundPatient = patientService.findPatientById(1);

        assertNull(foundPatient);
    }
}
