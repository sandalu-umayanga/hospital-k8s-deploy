package com.backend.controller;

import com.backend.dto.LoginDto;
import com.backend.dto.NurseDto;
import com.backend.dto.PatientDto;
import com.backend.dto.ResponseDto;
import com.backend.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/nurse")
@CrossOrigin
public class NurseController {

    @Autowired
    private NurseService nurseService;
    @Autowired
    private ResponseDto responseDto;
    @Autowired
    private com.backend.util.JwtUtils jwtUtils;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving a nurse in to the database
    // http://localhost:8080/api/v1/nurse/saveNurse

    /*
    when a nurse is successfully saved ==> responseDto = {code: "01", message: "Succeed the process", data: nurseDto}
    when a nurse already exists ==> responseDto = {code: "00", message: "Nurse already exists", data: nurseDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if any other error occurred ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @PostMapping("/saveNurse")
    public ResponseEntity<ResponseDto> saveNurse ( @RequestBody NurseDto nurseDto ) {
        try {
            String response = nurseService.saveNurse(nurseDto);
            if (response.equals("01")) {
                responseDto.setCode(response);
                responseDto.setMessage("Succeed the process");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse already exists");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.ALREADY_REPORTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error ocurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing nurse
    // http://localhost:8080/api/v1/nurse/updateNurse

    /*
    when a nurse is successfully updated ==> responseDto = {code: "02", message: "Nurse updated successfully", data: nurseDto}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: nurseDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if any other error occurred ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @PutMapping("/updateNurse")
    public ResponseEntity<ResponseDto> updateNurse ( @RequestBody NurseDto nurseDto ) {
        try {
            String response = nurseService.updateNurse(nurseDto);
            if (response.equals("02")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse updated successfully");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse not found");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing Nurse
    // http://localhost:8080/api/v1/nurse/deleteNurse

    /*
    when a nurse is successfully deleted ==> responseDto = {code: "04", message: "Nurse deleted successfully", data: nurseDto}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: nurseDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    if any other error occurred ==> responseDto = {code: "response", message: "Error occurred", data: null}
    */
    @DeleteMapping("/deleteNurse")
    public ResponseEntity<ResponseDto> deleteNurse ( @RequestBody NurseDto nurseDto ) {
        try {
            String response = nurseService.deleteNurse(nurseDto);
            if (response.equals("04")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse deleted successfully");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse not found");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a nurse by id
    // http://localhost:8080/api/v1/nurse/findNurseById/{id}

    /*
    when a nurse is found ==> responseDto = {code: "05", message: "Nurse found", data: nurseDto}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/findNurseById/{id}")
    public ResponseEntity<ResponseDto> findNurseById ( @PathVariable int id ) {
        try {
            NurseDto nurseDto = nurseService.findNurseById(id);
            if (nurseDto != null) {
                responseDto.setCode("05");
                responseDto.setMessage("Nurse found");
                responseDto.setData(nurseDto);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Nurse not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting patient list of a nurse
    // http://localhost:8080/api/v1/nurse/getPatientList/{id}

    /*
    when a patient list is found ==> responseDto = {code: "06", message: "Patient list found", data: patientList}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getPatientList/{id}")
    public ResponseEntity<ResponseDto> getPatientList ( @PathVariable int id ) {
        try {
            List<PatientDto> paientList = nurseService.getPatientList(id);
            if (paientList != null) {
                responseDto.setCode("06");
                responseDto.setMessage("Patient list found");
                responseDto.setData(paientList);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Nurse not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding all nurses
    // http://localhost:8080/api/v1/nurse/getAllNurses

    /*
    when nurses are found ==> responseDto = {code: "05", message: "Nurses found", data: nurseList}
    when no nurses found ==> responseDto = {code: "03", message: "No nurses found", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @GetMapping("/getAllNurses")
    public ResponseEntity<ResponseDto> getAllNurses () {
        try {
            List<NurseDto> nurseList = nurseService.getAllNurses();
            if (nurseList != null) {
                responseDto.setCode("05");
                responseDto.setMessage("Nurses found");
                responseDto.setData(nurseList);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("No nurses found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for logging using email and password
    // http://localhost:8080/api/v1/nurse/loginNurse

    /*
    when a nurse is successfully logged in ==> responseDto = {code: "07", message: "Nurse logged in", data: nurseDto}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: null}
    when a password is incorrect ==> responseDto = {code: "08", message: "Password is incorrect", data: null}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
    */
    @PostMapping("/loginNurse")
    public ResponseEntity<ResponseDto> loginNurse ( @RequestBody LoginDto loginDto ) {
        try {
            String response = nurseService.loginNurse(loginDto);
            if (response.equals("07")) {
                responseDto.setCode(response);
                responseDto.setMessage("logged in");
                responseDto.setData(nurseService.findNurseByEmail(loginDto.getEmail()));
                responseDto.setToken(jwtUtils.generateToken(loginDto.getEmail(), "NURSE"));
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("08")) {
                responseDto.setCode(response);
                responseDto.setMessage("Password is incorrect");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating the profile picture of a nurse
    // http://localhost:8080/api/v1/nurse/updateProfilePicture/{id}

    /*
    when a nurse is successfully updated ==> responseDto = {code: "09", message: "Profile picture updated", data: nurseDto}
    when a nurse not found ==> responseDto = {code: "03", message: "Nurse not found", data: nurseDto}
    when an error occurred ==> responseDto = {code: "000", message: "Error occurred", data: null}
   */
    @PutMapping("/updateProfilePicture/{id}")
    public ResponseEntity<ResponseDto> updateProfilePicture (@PathVariable int id, @RequestParam("profilePicture") MultipartFile profilePicture)  {
        try {
            String response = nurseService.updateProfilePicture(id, profilePicture.getBytes());
            if (response.equals("09")) {
                responseDto.setCode(response);
                responseDto.setMessage("Profile picture updated");
                responseDto.setData(nurseService.findNurseById(id));
                return new ResponseEntity<>(responseDto , HttpStatus.ACCEPTED);
            } else if (response.equals("03")) {
                responseDto.setCode(response);
                responseDto.setMessage("Nurse not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            responseDto.setData(e);
            return new ResponseEntity<>(responseDto , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting the profile picture of a nurse
    // http://localhost:8080/api/v1/nurse/getProfilePicture/{id}

    /*
    when the profile picture is found ==> return the profile picture
    when the doctor does not exist ==> return null
    */
    @GetMapping("/getProfilePicture/{id}")
    public byte[] getProfilePicture (@PathVariable int id) {
        return nurseService.getProfilePicture(id);
    }
}

