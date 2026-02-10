package com.backend.controller;

import com.backend.dto.ResponseDto;
import com.backend.dto.TreatmentDto;
import com.backend.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/treatment")
@CrossOrigin
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving treatment to the database
    // http://localhost:8080/api/v1/treatment/save
    /*
    when the user sends a POST request to the URL "/api/v1/treatment/save", the saveTreatment method is called.
    The method receives a TreatmentDto object as a parameter and saves it to the database.
    */
    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveTreatment(@RequestBody TreatmentDto treatmentDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response;
            response = treatmentService.saveTreatment(treatmentDto);
            if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("01")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("02")) {
                responseDto.setCode(response);
                responseDto.setMessage("Treatment saved successfully");
                responseDto.setData(treatmentDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Another error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            responseDto.setMessage("Failed to save treatment");
            e.printStackTrace();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all treatments from the database
    // http://localhost:8080/api/v1/treatment/all
    /*
    when the user sends a GET request to the URL "/api/v1/treatment/all", the getAllTreatments method is called.
    The method retrieves all treatments from the database and returns them as a list of TreatmentDto objects.
    */
    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllTreatments() {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setCode("07");
            responseDto.setMessage("Treatments retrieved successfully");
            responseDto.setData(treatmentService.getAllTreatments());
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Failed to retrieve treatments");
            e.printStackTrace();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating the treatment in the database
    // http://localhost:8080/api/v1/treatment/update
    /*
    when a treatment is updated ==> responseDto = {code: "06", message: "updated successfully", data: patientDto}
    when patient not found ==> responseDto = {code: "00", message: "Patient not found", data: null}
    when doctor not found ==> responseDto = {code: "01", message: "Doctor not found", data: null}
    when treatment not found ==> responseDto = {code: "05", message: "Treatment not found", data: null}
    when another error occurred ==> responseDto = {code: "03", message: "Another error occurred", data: null}
    if there is an exception ==> responseDto = {message: "Failed to update treatment"}
    */
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateTreatment(@RequestBody TreatmentDto treatmentDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response;
            response = treatmentService.updateTreatment(treatmentDto);
            if (response.equals("00")) {
                responseDto.setCode(response);
                responseDto.setMessage("Patient not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("01")) {
                responseDto.setCode(response);
                responseDto.setMessage("Doctor not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("05")) {
                responseDto.setCode(response);
                responseDto.setMessage("Treatment not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("06")) {
                responseDto.setCode(response);
                responseDto.setMessage("Treatment updated successfully");
                responseDto.setData(treatmentDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setCode(response);
                responseDto.setMessage("Another error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            responseDto.setMessage("Failed to update treatment");
            e.printStackTrace();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting the treatment from the database
    // http://localhost:8080/api/v1/treatment/delete
    /*
    when a treatment is deleted ==> responseDto = {code: "08", message: "deleted successfully", data: patientDto}
    when treatment not found ==> responseDto = {code: "05", message: "Treatment not found", data: null}
    if there is an exception ==> responseDto = {message: "Failed to delete treatment"}
    */
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteTreatment(@RequestBody TreatmentDto treatmentDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String response;
            response = treatmentService.deleteTreatment(treatmentDto);
            if (response.equals("05")) {
                responseDto.setCode(response);
                responseDto.setMessage("Treatment not found");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            } else if (response.equals("08")) {
                responseDto.setCode(response);
                responseDto.setMessage("Treatment deleted successfully");
                responseDto.setData(treatmentDto);
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setMessage("Another error occurred");
                responseDto.setData(null);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            responseDto.setMessage("Failed to delete treatment");
            e.printStackTrace();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<ResponseDto> getByPatientId(@PathVariable int id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setCode("09");
            responseDto.setMessage("Treatments retrieved successfully");
            responseDto.setData(treatmentService.getTreatmentsByPatientId(id));
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Failed to retrieve treatments");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
