package com.backend.controller;

import com.backend.dto.DoctorDto;
import com.backend.dto.LoginDto;
import com.backend.dto.PatientDto;
import com.backend.dto.ResponseDto;
import com.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("api/v1/doctor")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ResponseDto  responseDto;
    @Autowired
    private com.backend.util.JwtUtils jwtUtils;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving a doctor in to the database
    // http://localhost:8080/api/v1/doctor/saveDoctor

    /*
     when a doctor is successfully saved ==> responseDto = {code: "01", message: "Succeed the process", data: doctorDto}
     when a doctor already exists ==> responseDto = {code: "00", message: "Doctor already exists", data: doctorDto}
     when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
     if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
     */
    @PostMapping("/saveDoctor")
    public ResponseEntity<ResponseDto> saveDoctor(@RequestBody DoctorDto doctorDto) {
        try {
            String response = doctorService.saveDoctor(doctorDto);
            if (response.equals("01")) {
                responseDto.setCode(response);
                responseDto.setMessage("Succeed the process");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor already exists");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ALREADY_REPORTED);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing doctor
    // http://localhost:8080/api/v1/doctor/updateDoctor

    /*
    when a doctor is successfully updated ==> responseDto = {code: "02", message: "Doctor updated successfully", data: doctorDto}
    when a doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: doctorDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @PutMapping("/updateDoctor")
    public ResponseEntity<ResponseDto> updateDoctor(@RequestBody DoctorDto doctorDto) {
        try {
            String response = doctorService.updateDoctor(doctorDto);
            if (response.equals("02")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor updated successfully");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing Doctor
    // http://localhost:8080/api/v1/doctor/deleteDoctor

    /*
    when a doctor is successfully deleted ==> responseDto = {code: "04", message: "Doctor deleted successfully", data: doctorDto}
    when a doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: doctorDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @DeleteMapping("/deleteDoctor")
    public ResponseEntity<ResponseDto> deleteDoctor(@RequestBody DoctorDto doctorDto) {
        try {
            String response = doctorService.deleteDoctor(doctorDto);
            if (response.equals("04")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor deleted successfully");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(doctorDto);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a doctor by id
    // http://localhost:8080/api/v1/doctor/findDoctorById/{id}

    /*
    when a doctor is found ==> responseDto = {code: "05", message: "Doctor found successfully", data: doctorDto}
    when a doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/findDoctorById/{id}")
    public ResponseEntity<ResponseDto> findDoctorById(@PathVariable int id) {
        try {
            DoctorDto doctorDto = doctorService.findDoctorById(id);
            if (doctorDto != null) {
                responseDto.setCode("05");
                responseDto.setMessage("Doctor found successfully");
                responseDto.setData(doctorDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Doctor not found");
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting patient list of a doctor
    // http://localhost:8080/api/v1/doctor/getPatientList/{id}

    /*
    when a patient list is found ==> responseDto = {code: "08", message: "Patient list found", data: patientList}
    when a doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getPatientList/{id}")
    public ResponseEntity<ResponseDto> getPatientList(@PathVariable int id) {
        try {
            List<PatientDto> paientList = doctorService.getPatientList(id);
            if (paientList != null) {
                responseDto.setCode("08");
                responseDto.setMessage("Patient list found");
                responseDto.setData(paientList);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Doctor not found");
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all doctors
    // http://localhost:8080/api/v1/doctor/getAllDoctors

    /*
    when doctors are found ==> responseDto = {code: "09", message: "Doctors found", data: doctorList}
    when there are no doctors ==> responseDto = {code: "03", message: "No doctors found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getAllDoctors")
    public ResponseEntity<ResponseDto> getAllDoctors() {
        try {
            List<DoctorDto> doctorList = doctorService.getAllDoctors();
            if (doctorList != null) {
                responseDto.setCode("09");
                responseDto.setMessage("Doctors found");
                responseDto.setData(doctorList);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("No doctors found");
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for login using email and password
    // http://localhost:8080/api/v1/doctor/loginDoctor

    /*
    when a doctor is logged in ==> responseDto = {code: "10", message: "Doctor logged in", data: doctorDto}
    when the password is incorrect ==> responseDto = {code: "11", message: "Password incorrect", data: null}
    when the doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @PostMapping("/loginDoctor")
    public ResponseEntity<ResponseDto> loginDoctor( @RequestBody LoginDto loginDto) {
        try {
            String response = doctorService.loginDoctor(loginDto);
            if (response.equals("10")) {
                responseDto.setCode(response);
                responseDto.setMessage("logged in");
                responseDto.setData(doctorService.findDoctorByEmail(loginDto.getEmail()));
                responseDto.setToken(jwtUtils.generateToken(loginDto.getEmail(), "DOCTOR"));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("11")) {
                responseDto.setCode(response);
                responseDto.setMessage("Password incorrect");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating the profile picture of a doctor
    // http://localhost:8080/api/v1/doctor/updateProfilePicture/{id}

    /*
    when the profile picture is updated ==> responseDto = {code: "12", message: "Profile picture updated", data: doctorDto}
    when the doctor does not exist ==> responseDto = {code: "03", message: "Doctor not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @PutMapping("/updateProfilePicture/{id}")
    public ResponseEntity<ResponseDto> updateProfilePicture(@PathVariable int id, @RequestParam("profilePicture") MultipartFile profilePicture) {
        try {
            String response = doctorService.updateProfilePicture(id, profilePicture.getBytes());
            if (response.equals("12")) {
                responseDto.setCode(response);
                responseDto.setMessage("Profile picture updated");
                responseDto.setData(doctorService.findDoctorById(id));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(null);
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
            responseDto.setData(e);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting the profile picture of a doctor
    // http://localhost:8080/api/v1/doctor/getProfilePicture/{id}

    /*
    when the profile picture is found ==> return the profile picture
    when the doctor does not exist ==> return null
    */
    @GetMapping("/getProfilePicture/{id}")
    public byte[] getProfilePicture(@PathVariable int id) {
        return doctorService.getProfilePicture(id);
    }

}
