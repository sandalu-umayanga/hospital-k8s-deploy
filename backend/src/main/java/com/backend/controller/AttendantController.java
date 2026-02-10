package com.backend.controller;

import com.backend.dto.AttendantDto;
import com.backend.dto.LoginDto;
import com.backend.dto.PatientDto;
import com.backend.dto.ResponseDto;
import com.backend.service.AttendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attendant")
@CrossOrigin
public class AttendantController {

    @Autowired
    private AttendantService attendantService;
    @Autowired
    private ResponseDto  responseDto;
    @Autowired
    private com.backend.util.JwtUtils jwtUtils;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving an attendant in to the database
    // http://localhost:8080/api/v1/attendant/saveAttendant

    /*
    when an attendant is successfully saved ==> responseDto = {code: "01", message: "Succeed the process", data: attendantDto}
    when an attendant already exists ==> responseDto = {code: "00", message: "Attendant already exists", data: attendantDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @PostMapping("/saveAttendant")
    public ResponseEntity<ResponseDto> saveAttendant(@RequestBody AttendantDto attendantDto) {
        try {
            String response = attendantService.saveAttendant(attendantDto);
            if (response.equals("01")) {
                responseDto.setCode(response);
                responseDto.setMessage("Succeed the process");
                responseDto.setData(attendantDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant already exists");
                responseDto.setData(attendantDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ALREADY_REPORTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error ocurred");
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

    // method for updating an existing attendant
    // http://localhost:8080/api/v1/attendant/updateAttendant

    /*
    when an attendant is successfully updated ==> responseDto = {code: "02", message: "Attendant updated successfully", data: attendantDto}
    when an attendant does not exist ==> responseDto = {code: "03", message: "Attendant not found", data: attendantDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @PutMapping("/updateAttendant")
    public ResponseEntity<ResponseDto> updateAttendant(@RequestBody AttendantDto attendantDto) {
        try {
            String response = attendantService.updateAttendant(attendantDto);
            if (response.equals("02")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant updated successfully");
                responseDto.setData(attendantDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant not found");
                responseDto.setData(attendantDto);
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

    // method for deleting an existing Attendant
    // http://localhost:8080/api/v1/attendant/deleteAttendant

    /*
    when an attendant is successfully deleted ==> responseDto = {code: "04", message: "Attendant deleted successfully", data: attendantDto}
    when an attendant does not exist ==> responseDto = {code: "03", message: "Attendant not found", data: attendantDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if there is any other error ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @DeleteMapping("/deleteAttendant")
    public ResponseEntity<ResponseDto> deleteAttendant(@RequestBody AttendantDto attendantDto) {
        try {
            String response = attendantService.deleteAttendant(attendantDto);
            if (response.equals("04")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant deleted successfully");
                responseDto.setData(attendantDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant not found");
                responseDto.setData(attendantDto);
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

    // method for finding an attendant by id
    // http://localhost:8080/api/v1/attendant/findAttendantById/{id}

    /*
    when an attendant is found ==> responseDto = {code: "05", message: "Attendant found", data: attendantDto}
    when an attendant does not exist ==> responseDto = {code: "03", message: "Attendant not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/findAttendantById/{id}")
    public ResponseEntity<ResponseDto> findAttendantById(@PathVariable int id) {
        try {
            AttendantDto attendantDto = attendantService.findAttendantById(id);
            if (attendantDto != null) {
                responseDto.setCode("05");
                responseDto.setMessage("Attendant found");
                responseDto.setData(attendantDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Attendant not found");
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

    // method for getting patient list of an attendant
    // http://localhost:8080/api/v1/attendant/getPatientList/{id}

    /*
    when a patient list is found ==> responseDto = {code: "06", message: "Patient list found", data: patientList}
    when an attendant does not exist ==> responseDto = {code: "03", message: "Attendant not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getPatientList/{id}")
    public ResponseEntity<ResponseDto> getPatientList(@PathVariable int id) {
        try {
            List<PatientDto> paientList = attendantService.getPatientList(id);
            if (paientList != null) {
                responseDto.setCode("06");
                responseDto.setMessage("Patient list found");
                responseDto.setData(paientList);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Attendant not found");
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

    // method for getting all attendants
    // http://localhost:8080/api/v1/attendant/getAllAttendants

    /*
    when attendants are found ==> responseDto = {code: "05", message: "Attendants found", data: attendantList}
    when there is no attendant ==> responseDto = {code: "03", message: "No attendant found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getAllAttendants")
    public ResponseEntity<ResponseDto> getAllAttendants() {
        try {
            List<AttendantDto> attendantList = attendantService.getAllAttendants();
            if (attendantList != null) {
                responseDto.setCode("05");
                responseDto.setMessage("Attendants found");
                responseDto.setData(attendantList);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("No attendant found");
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

    // method for login using email and password
    // http://localhost:8080/api/v1/attendant/loginAttendant

    /*
    when an attendant is logged in ==> responseDto = {code: "10", message: "Attendant logged in", data: attendantDto}
    when the password is incorrect ==> responseDto = {code: "11", message: "Password incorrect", data: null}
    when the attendant does not exist ==> responseDto = {code: "03", message: "Attendant not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @PostMapping("/loginAttendant")
    public ResponseEntity<ResponseDto> loginAttendant(@RequestBody LoginDto loginDto) {
        try {
            String response = attendantService.loginAttendant(loginDto);
            if (response.equals("10")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant logged in");
                responseDto.setData(attendantService.findAttendantByEmail(loginDto.getEmail()));
                responseDto.setToken(jwtUtils.generateToken(loginDto.getEmail(), "ATTENDANT"));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("11")) {
                responseDto.setCode(response);
                responseDto.setMessage("Password incorrect");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Attendant not found");
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
}
