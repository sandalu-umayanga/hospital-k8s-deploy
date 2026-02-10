package com.backend.controller;

import com.backend.dto.LoginDto;
import com.backend.dto.PatientDto;
import com.backend.dto.ResponseDto;
import com.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/patient")
@CrossOrigin

public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private com.backend.util.JwtUtils jwtUtils;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving a patient to the database
    // http://localhost:8080/api/v1/patient/savePatient
    // http://localhost:8080/patientRegister.html

    /*
    when a patient is successfully saved ==> responseDto = {code: "01", message: "Succeed the process!", data: patientDto}
    when a patient already exists ==> responseDto = {code: "00", message: "Patient already exists", data: patientDto}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error occurs ==> responseDto = {code: "error response", message: "Error occurred", data: null}
    */
    @PostMapping("/savePatient")
    public ResponseEntity<ResponseDto> savePatient(@RequestBody PatientDto patientDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response = patientService.savePatient(patientDto);
            if (response.equals("01")){
                responseDto.setCode(response);
                responseDto.setMessage("Succeed the process!");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient already exists");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing patient
    // http://localhost:8080/api/v1/patient/updatePatient
    // http://localhost:8080/indexUpdatePatient.html

    /*
    when a patient is successfully updated ==> responseDto = {code: "02", message: "Patient updated successfully", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: patientDto}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error occurs ==> responseDto = {code: "error response", message: "Error occurred", data: null}
    */
    @PutMapping("/updatePatient")
    public ResponseEntity<ResponseDto> updatePatient(@RequestBody PatientDto patientDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response = patientService.updatePatient(patientDto);
            if (response.equals("02")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient updated successfully");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient not found");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Not found error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing Patient
    // http://localhost:8080/api/v1/patient/deletePatient
    // http://localhost:8080/indexDeletePatient.html

    /*
    when a patient is successfully deleted ==> responseDto = {code: "04", message: "Patient deleted successfully", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: patientDto}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error occurs ==> responseDto = {code: "error response", message: "Error occurred", data: null}
    */
    @DeleteMapping("/deletePatient")
    public ResponseEntity<ResponseDto> deletePatient(@RequestBody PatientDto patientDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response = patientService.deletePatient(patientDto);
            if (response.equals("04")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient deleted successfully");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient not found");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding patient by id
    // http://localhost:8080/api/v1/patient/findPatientById/{id}
    // http://localhost:8080/indexFindbyIDpatient.html

    /*
    when a patient is found ==> responseDto = {code: "06", message: "Patient found", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: null}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/findPatientById/{id}")
    public ResponseEntity<ResponseDto> findPatientById(@PathVariable int id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            PatientDto patientDto = patientService.findPatientById(id);
            if (patientDto != null) {
                responseDto.setCode("06");
                responseDto.setMessage("Patient found");
                responseDto.setData(patientDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting doctor list of a patient
    // http://localhost:8080/api/v1/patient/getDoctorList/{id}
    // http://localhost:8080/indexGetDoctorListofaPatient.html

    /*
    when a doctor list is found ==> responseDto = {code: "08", message: "Doctor list found", data: doctorDtoList}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: null}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getDoctorList/{id}")
    public ResponseEntity<ResponseDto> getDoctorList(@PathVariable int id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (patientService.findPatientById(id) == null) {
                responseDto.setCode("03");
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode("08");
                responseDto.setMessage("Doctor list found");
                responseDto.setData(patientService.getDoctorList(id));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting patient by ID
    // http://localhost:8080/api/v1/patient/deletePatientById/{id}
    // http://localhost:8080/indexDeletePatientByID.html

    /*
    when a patient is successfully deleted ==> responseDto = {code: "04", message: "Patient deleted successfully", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: patientDto}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */

    @DeleteMapping("/deletePatientById/{id}")
    public ResponseEntity<ResponseDto> deletePatientById(@PathVariable int id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response = patientService.deletePatientById(id);
            if (response.equals("04")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient deleted successfully");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all patients from the database
    // http://localhost:8080/api/v1/patient/getAllPatients
    // http://localhost:8080/indexGetAllPatients.html

    /*
    when all patients are found ==> responseDto = {code: "09", message: "Success", data: patientDtoList}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getAllPatients")
    public ResponseEntity<ResponseDto> getAllPatients() {
        ResponseDto responseDto = new ResponseDto();
        try {
            List<PatientDto> patients = patientService.getAllPatients();
            responseDto.setCode("09");
            responseDto.setMessage("Success");
            responseDto.setData(patients);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding patient by name
    // http://localhost:8080/api/v1/patient/findPatientByName/{name}
    // http://localhost:8080/indexFindbyNamePatient.html

    /*
    when a patient is found ==> responseDto = {code: "06", message: "Patient found", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: null}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/findPatientByName/{name}")
    public ResponseEntity<ResponseDto> findPatientByName(@PathVariable String name) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (name == null) {
                responseDto.setCode("03");
                responseDto.setMessage("Invalid search term");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            List<PatientDto> patients = patientService.findPatientByName(name);
            if (patients.isEmpty()) {
                responseDto.setCode("03");
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode("06");
                responseDto.setMessage("Patient found");
                responseDto.setData(patients);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Detailed logging
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for logging in a patient using password and email
    // http://localhost:8080/api/v1/patient/loginPatient
    // http://localhost:8080/patientLogin.html

    /*
    when a patient is successfully logged in ==> responseDto = {code: "10", message: "Patient logged in successfully", data: patientDto}
    when a patient does not exist ==> responseDto = {code: "03", message: "Patient not found", data: null}
    when a password is incorrect ==> responseDto = {code: "11", message: "Password incorrect", data: null}
    when an error occurs ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @PostMapping("/loginPatient")
    public ResponseEntity<ResponseDto> loginPatient(@RequestBody LoginDto loginDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response = patientService.loginPatient(loginDto);
            if (Objects.equals(response , "10")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient logged in successfully");
                responseDto.setData(loginDto);
                responseDto.setToken(jwtUtils.generateToken(loginDto.getEmail(), "PATIENT"));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (Objects.equals(response , "11")) {
                responseDto.setCode(response);
                responseDto.setMessage("Password incorrect");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
