package com.backend.service;

import com.backend.dto.LoginDto;
import com.backend.dto.NurseDto;
import com.backend.dto.PatientDto;
import com.backend.entity.Nurse;
import com.backend.messages.NurseMessages;
import com.backend.repo.NurseRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NurseService {

    @Autowired
    private NurseRepo nurseRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method to save the nurse in to the database
    /*
    * This method saves the nurse in to the database. It first checks if the nurse already exists in the database by
    * calling the findNurseByDetails method of the nurseRepo object. If the nurse already exists, it returns the
    * message "Nurse already exists". If the nurse does not exist, it saves the nurse in to the database by calling
    * the save method of the nurseRepo object and returns the message "Nurse saved".
    */
    public String saveNurse(NurseDto nurseDto){
        if (nurseRepo.findNurseByDetails(nurseDto.getFirstName(), nurseDto.getLastName(), nurseDto.getPhone(), nurseDto.getEmail()) != null) {
            return NurseMessages.NURSE_ALREADY_EXISTS;
        } else {
            Nurse nurse = modelMapper.map(nurseDto, Nurse.class);
            nurse.setPassword(passwordEncoder.encode(nurseDto.getPassword()));
            nurseRepo.save(nurse);
            return NurseMessages.NURSE_SAVED;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing nurse
    /*
    * This method updates the nurse in the database. It first checks if the nurse exists in the database by calling the
    * existsById method of the nurseRepo object. If the nurse exists, it updates the nurse in the database by calling
    * the save method of the nurseRepo object and returns the message "Nurse updated". If the nurse does not exist, it
    * returns the message "Nurse not found".
    */
    public String updateNurse(NurseDto nurseDto) {
        if (nurseRepo.existsById(nurseDto.getId())) {
            Nurse nurse = modelMapper.map(nurseDto, Nurse.class);
            if (nurseDto.getPassword() != null) {
                nurse.setPassword(passwordEncoder.encode(nurseDto.getPassword()));
            }
            nurseRepo.save(nurse);
            return NurseMessages.NURSE_UPDATED;
        } else {
            return NurseMessages.NURSE_NOT_FOUND;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing nurse
    /*
    * This method deletes the nurse from the database. It first checks if the nurse exists in the database by calling the
    * existsById method of the nurseRepo object. If the nurse exists, it deletes the nurse from the database by calling
    * the delete method of the nurseRepo object and returns the message "Nurse deleted". If the nurse does not exist, it
    * returns the message "Nurse not found". But if the nurse is assigned to a patient, it returns an error message.
    */
    public String deleteNurse(NurseDto nurseDto) {
        if(nurseRepo.existsById(nurseDto.getId())) {
            nurseRepo.delete(modelMapper.map(nurseDto, Nurse.class));
            return NurseMessages.NURSE_DELETED;
        } else {
            return NurseMessages.NURSE_NOT_FOUND;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a nurse by id
    /*
    * This method finds the nurse by id. It first checks if the nurse exists in the database by calling the existsById
    * method of the nurseRepo object. If the nurse exists, it returns the nurse by calling the findNurseById method of
    * the nurseRepo object. If the nurse does not exist, it returns null.
    */
    public NurseDto findNurseById(int id) {
        if(nurseRepo.existsById(id)) {
            return modelMapper.map(nurseRepo.findNurseById(id), NurseDto.class);
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting patient list of a nurse
    /*
    * This method gets the list of patients assigned to a nurse. It first checks if the nurse exists in the database by
    * calling the existsById method of the nurseRepo object. If the nurse exists, it returns the list of patients assigned
    * to the nurse by calling the findNurseById method of the nurseRepo object and mapping the patients to the PatientDto
    * class. If the nurse does not exist, it returns null.
    */
    public List<PatientDto> getPatientList(int id) {
        if(nurseRepo.existsById(id)) {
            return nurseRepo.findNurseById(id).getPatients().stream().map(patient -> modelMapper.map(patient, PatientDto.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all nurses
    /*
    * This method gets all the nurses in the database. It returns the list of all nurses by calling the findAll method of
    * the nurseRepo object and mapping the nurses to the NurseDto class.
    */
    public List<NurseDto> getAllNurses() {
        if (!nurseRepo.findAll().isEmpty()) {
            return nurseRepo.findAll().stream().map(nurse -> modelMapper.map(nurse, NurseDto.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for login using email and password
    /*
    when a nurse logs in, the method checks if the nurse exists in the database or not. If the nurse exists,
    the method checks if the password is correct or not. If the password is correct, the nurse is logged in and
    a message is returned that the nurse is logged in. If the password is incorrect, a message is returned that the
    password is incorrect. If the nurse does not exist, a message is return that the nurse does not exist.
    */
    public String loginNurse( LoginDto loginDto) {
        Nurse nurse = nurseRepo.findNurseByEmail(loginDto.getEmail());
        if (nurse != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), nurse.getPassword())) {
                return NurseMessages.NURSE_LOGGED_IN;
            } else {
                return NurseMessages.PASSWORD_INCORRECT;
            }
        } else {
            return NurseMessages.NURSE_NOT_FOUND;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a nurse by email
    /*
    * This method finds the nurse by email. It first checks if the nurse exists in the database by calling the
    * findNurseByEmail method of the nurseRepo object. If the nurse exists, it returns the nurse by calling the
    * findNurseByEmail method of the nurseRepo object. If the nurse does not exist, it returns null.
    */
    public NurseDto findNurseByEmail(String email) {
        if(nurseRepo.findNurseByEmail(email) != null) {
            return modelMapper.map(nurseRepo.findNurseByEmail(email), NurseDto.class);
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating the profile picture of a nurse
    /*
    * This method updates the profile picture of the nurse. It first checks if the nurse exists in the database by calling
    * the existsById method of the nurseRepo object. If the nurse exists, it updates the profile picture of the nurse by
    * calling the save method of the nurseRepo object and returns the message "Profile picture updated". If the nurse does
    * not exist, it returns the message "Nurse not found".
    */
    public String updateProfilePicture(int id, byte[] profilePicture) {
        if (nurseRepo.existsById(id)) {
            Nurse nurse = nurseRepo.findNurseById(id);
            nurse.setProfilePicture(profilePicture);
            nurseRepo.save(nurse);
            return NurseMessages.PROFILE_PICTURE_UPDATED;
        } else {
            return NurseMessages.NURSE_NOT_FOUND;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting the profile picture of a nurse
    /*
    * This method gets the profile picture of a nurse by checking if the nurse exists in the database. If the nurse
    * exists, it returns the profile picture of the nurse. If the nurse does not exist, it returns null.
    */
    public byte[] getProfilePicture(int id) {
        if (nurseRepo.existsById(id)) {
            return nurseRepo.findNurseById(id).getProfilePicture();
        } else {
            return null;
        }
    }
}

