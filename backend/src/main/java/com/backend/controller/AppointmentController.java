package com.backend.controller;

import com.backend.dto.AppointmentDto;
import com.backend.dto.ResponseDto;
import com.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ResponseDto responseDto;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveAppointment(@RequestBody AppointmentDto appointmentDto) {
        try {
            String res = appointmentService.saveAppointment(appointmentDto);
            if (res.equals("02")) {
                responseDto.setCode(res);
                responseDto.setMessage("Appointment saved successfully");
                responseDto.setData(appointmentDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (res.equals("00")) {
                responseDto.setCode(res);
                responseDto.setMessage("Patient not found");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(res);
                responseDto.setMessage("Doctor not found");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred: " + e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<ResponseDto> getByPatient(@PathVariable int id) {
        try {
            List<AppointmentDto> appointments = appointmentService.getAppointmentsByPatient(id);
            responseDto.setCode("05");
            responseDto.setMessage("Success");
            responseDto.setData(appointments);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<ResponseDto> getByDoctor(@PathVariable int id) {
        try {
            List<AppointmentDto> appointments = appointmentService.getAppointmentsByDoctor(id);
            responseDto.setCode("05");
            responseDto.setMessage("Success");
            responseDto.setData(appointments);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseDto> updateStatus(@PathVariable int id, @RequestParam String status) {
        try {
            String res = appointmentService.updateAppointmentStatus(id, status);
            if (res.equals("04")) {
                responseDto.setCode(res);
                responseDto.setMessage("Status updated");
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            } else {
                responseDto.setCode(res);
                responseDto.setMessage("Appointment not found");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
