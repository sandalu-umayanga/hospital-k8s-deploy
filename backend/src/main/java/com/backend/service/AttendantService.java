package com.backend.service;

import com.backend.dto.AttendantDto;
import com.backend.dto.PatientDto;
import com.backend.dto.LoginDto;
import com.backend.entity.Attendant;
import com.backend.messages.AttendantMessages;
import com.backend.repo.AttendantRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendantService {

    @Autowired
    private AttendantRepo attendantRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method to save the attendant in to the database
    /*
    * This method saves the attendant in to the database by checking if the attendant already exists in the database.
    * If the attendant already exists, it returns a message that the attendant already exists. If the attendant does not
    * exist, it saves the attendant in to the database and returns a message that the attendant has been saved.
    */
    public String saveAttendant(AttendantDto attendantDto){
        if (attendantRepo.findAttendantByDetails(attendantDto.getFirstName(), attendantDto.getLastName(), attendantDto.getPhone(), attendantDto.getEmail()) != null) {
            return AttendantMessages.ATTENDANT_ALREADY_EXISTS;
        } else {
            Attendant attendant = modelMapper.map(attendantDto, Attendant.class);
            attendant.setPassword(passwordEncoder.encode(attendantDto.getPassword()));
            attendantRepo.save(attendant);
            return AttendantMessages.ATTENDANT_SAVED;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing attendant
    /*
    * This method updates an existing attendant in the database by checking if the attendant exists in the database.
    * If the attendant exists, it updates the attendant in the database and returns a message that the attendant has been
    * updated. If the attendant does not exist, it returns a message that the attendant does not exist.
    */
    public String updateAttendant(AttendantDto attendantDto) {
        if (attendantRepo.existsById(attendantDto.getId())) {
            Attendant attendant = modelMapper.map(attendantDto, Attendant.class);
            if(attendantDto.getPassword() != null) {
                attendant.setPassword(passwordEncoder.encode(attendantDto.getPassword()));
            }
            attendantRepo.save(attendant);
            return AttendantMessages.ATTENDANT_UPDATED;
        } else {
            return AttendantMessages.ATTENDANT_NOT_FOUND;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing attendant
    /*
    * This method deletes an existing attendant in the database by checking if the attendant exists in the database.
    * If the attendant exists, it deletes the attendant from the database and returns a message that the attendant has
    * been deleted. If the attendant does not exist, it returns a message that the attendant does not exist.
    * This method also deletes the attendant from the database. If an attendant has patients, the attendant cannot be
    * deleted.
    */
    public String deleteAttendant(AttendantDto attendantDto) {
        if(attendantRepo.existsById(attendantDto.getId())) {
            attendantRepo.delete(modelMapper.map(attendantDto, Attendant.class));
            return AttendantMessages.ATTENDANT_DELETED;
        } else {
            return AttendantMessages.ATTENDANT_NOT_FOUND;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding an attendant by id
    /*
    * This method finds an attendant by id by checking if the attendant exists in the database. If the attendant exists,
    * it returns the attendant. If the attendant does not exist, it returns null.
    */
    public AttendantDto findAttendantById(int id) {
        if (attendantRepo.existsById(id)) {
            return modelMapper.map(attendantRepo.findAttendantById(id), AttendantDto.class);
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting patient list of an attendant
    /*
    * This method gets the patient list of an attendant by checking if the attendant exists in the database. If the
    * attendant exists, it returns the patient list of the attendant. If the attendant does not exist, it returns null.
    */
    public List<PatientDto> getPatientList(int id) {
            if (attendantRepo.existsById(id)) {
                return attendantRepo.findAttendantById(id).getPatients().stream().map(patient -> modelMapper.map(patient, PatientDto.class)).collect(Collectors.toList());
            } else {
                return null;
            }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all attendants
    /*
    * This method gets all the attendants in the database and returns the list of attendants.
    * If there are no attendants in the database, it returns null.
    */
    public List<AttendantDto> getAllAttendants() {
        if (attendantRepo.findAll().isEmpty()) {
            return null;
        } else {
            return attendantRepo.findAll().stream().map(attendant -> modelMapper.map(attendant, AttendantDto.class)).collect(Collectors.toList());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for login using email and password
    /*
    * This method logs in an attendant using their email and password. It checks if the attendant exists in the database
    * and if the password matches the one in the database. If the attendant exists and the password matches, it returns
    * a message that the attendant has been logged in. If the password does not match, it returns a message that the
    * password is incorrect. If the attendant does not exist, it returns a message that the attendant does not exist.
    */
    public String loginAttendant(LoginDto loginDto) {
        Attendant attendant = attendantRepo.findAttendantByEmail(loginDto.getEmail());
        if (attendant != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), attendant.getPassword())) {
                return "10";
            } else {
                return "11";
            }
        } else {
            return "03";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding an attendant by email
    /*
    * This method finds an attendant by email by checking if the attendant exists in the database. If the attendant exists,
    * it returns the attendant. If the attendant does not exist, it returns null.
    */
    public AttendantDto findAttendantByEmail(String email) {
        if (attendantRepo.findAttendantByEmail(email) != null) {
            return modelMapper.map(attendantRepo.findAttendantByEmail(email), AttendantDto.class);
        } else {
            return null;
        }
    }

}

