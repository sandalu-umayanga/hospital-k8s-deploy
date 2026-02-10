package com.backend.service;

import com.backend.dto.AppointmentDto;
import com.backend.entity.Appointment;
import com.backend.entity.Doctor;
import com.backend.entity.Patient;
import com.backend.repo.AppointmentRepo;
import com.backend.repo.DoctorRepo;
import com.backend.repo.PatientRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private ModelMapper modelMapper;

    public String saveAppointment(AppointmentDto appointmentDto) {
        Patient patient = patientRepo.findById(appointmentDto.getPatientId()).orElse(null);
        Doctor doctor = doctorRepo.findById(appointmentDto.getDoctorId()).orElse(null);

        if (patient == null) return "00"; // Patient not found
        if (doctor == null) return "01"; // Doctor not found

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateTime(appointmentDto.getDateTime());
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setReason(appointmentDto.getReason());

        appointmentRepo.save(appointment);
        return "02"; // Success
    }

    public List<AppointmentDto> getAppointmentsByPatient(int patientId) {
        return appointmentRepo.findByPatientId(patientId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctor(int doctorId) {
        return appointmentRepo.findByDoctorId(doctorId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public String updateAppointmentStatus(int appointmentId, String status) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElse(null);
        if (appointment == null) return "03"; // Not found
        appointment.setStatus(status);
        appointmentRepo.save(appointment);
        return "04"; // Updated
    }

    private AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
        dto.setDateTime(appointment.getDateTime());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());
        return dto;
    }
}
