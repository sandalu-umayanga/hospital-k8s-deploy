package com.backend.service;

import com.backend.dto.DoctorDto;
import com.backend.dto.LoginDto;
import com.backend.dto.PatientDto;
import com.backend.entity.Doctor;
import com.backend.messages.DoctorMessages;
import com.backend.repo.DoctorRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method to save the doctor in to the database
    /*
    * This method saves the doctor in to the database by checking if the doctor already exists in the database.
    * If the doctor already exists, it returns a message that the doctor already exists. If the doctor does not
    * exist, it saves the doctor in to the database and returns a message that the doctor has been saved.
    */
    public String saveDoctor(DoctorDto doctorDto){
        if (doctorRepo.findDoctorByDetails(doctorDto.getFirstName(), doctorDto.getLastName(), doctorDto.getPhone(), doctorDto.getEmail()) != null) {
            return DoctorMessages.DOCTOR_ALREADY_EXISTS;
        } else {
            Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
            doctor.setPassword(passwordEncoder.encode(doctorDto.getPassword()));
            doctorRepo.save(doctor);
            return DoctorMessages.DOCTOR_SAVED;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing doctor
    /*
    * This method updates an existing doctor in the database by checking if the doctor exists in the database.
    * If the doctor exists, it updates the doctor in the database and returns a message that the doctor has been
    * updated. If the doctor does not exist, it returns a message that the doctor does not exist.
    */
    public String updateDoctor(DoctorDto doctorDto) {
        if (doctorRepo.existsById(doctorDto.getId())) {
            Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
            if (doctorDto.getPassword() != null) {
                doctor.setPassword(passwordEncoder.encode(doctorDto.getPassword()));
            }
            doctorRepo.save(doctor);
            return DoctorMessages.DOCTOR_UPDATED;
        } else {
            return DoctorMessages.DOCTOR_NOT_FOUND;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing doctor
    /*
    * This method deletes an existing doctor in the database by checking if the doctor exists in the database.
    * If the doctor exists, it deletes the doctor in the database and returns a message that the doctor has been
    * deleted. If the doctor does not exist, it returns a message that the doctor does not exist. If the doctor
    * has patients, doctor can't be deleted.
    */
    public String deleteDoctor(DoctorDto doctorDto) {
        if(doctorRepo.existsById(doctorDto.getId())) {
            doctorRepo.delete(modelMapper.map(doctorDto, Doctor.class));
            return DoctorMessages.DOCTOR_DELETED;
        } else {
            return DoctorMessages.DOCTOR_NOT_FOUND;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a doctor by id
    /*
    * This method finds a doctor by id in the database. If the doctor exists, it returns the doctor. If the doctor does
    * not exist, it returns null.
    */
    public DoctorDto findDoctorById(int id) {
        if (doctorRepo.findDoctorById(id) != null) {
            return modelMapper.map(doctorRepo.findDoctorById(id), DoctorDto.class);
        } else {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding the patient list of a doctor
    /*
    * This method finds the patient list of a doctor by checking if the doctor exists in the database. If the doctor
    * exists, it returns the patient list of the doctor. If the doctor does not exist, it returns null.
    */
    public List<PatientDto> getPatientList(int id) {
        if (doctorRepo.existsById(id)) {
            return doctorRepo.findDoctorById(id).getPatients().stream().map(patient -> modelMapper.map(patient, PatientDto.class)).toList();
        } else {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding all doctors
    /*
    * This method finds all doctors in the database. If there are doctors in the database, it returns the list of doctors.
    * If there are no doctors in the database, it returns null.
    */
    public List<DoctorDto> getAllDoctors() {
        if (!doctorRepo.findAll().isEmpty()) {
            return doctorRepo.findAll().stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class)).toList();
        } else {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for login using email and password
    /*
    when a doctor logs in, the method checks if the doctor exists in the database or not. If the doctor exists,
    the method checks if the password is correct or not. If the password is correct, the doctor is logged in and
    a message is returned that the doctor is logged in. If the password is incorrect, a message is returned that the
    password is incorrect. If the doctor does not exist, a message is returned that the doctor does not exist.
    */
    public String loginDoctor( LoginDto loginDto) {
        Doctor doctor = doctorRepo.findDoctorByEmail(loginDto.getEmail());
        if (doctor != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), doctor.getPassword())) {
                return DoctorMessages.DOCTOR_LOGGED_IN;
            } else {
                return DoctorMessages.PASSWORD_INCORRECT;
            }
        } else {
            return DoctorMessages.DOCTOR_NOT_FOUND;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a doctor by email
    /*
    * This method finds the doctor by email. It first checks if the doctor exists in the database by calling the
    * findDoctorByEmail method of the doctorRepo object. If the doctor exists, it returns the doctor by calling the
    * findDoctorByEmail method of the doctorRepo object. If the doctor does not exist, it returns null.
    */
    public DoctorDto findDoctorByEmail(String email) {
        if(doctorRepo.findDoctorByEmail(email) != null) {
            return modelMapper.map(doctorRepo.findDoctorByEmail(email), DoctorDto.class);
        } else {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating profile picture of a doctor
    /*
    * This method updates the profile picture of a doctor by checking if the doctor exists in the database. If the doctor
    * exists, it updates the profile picture of the doctor and returns a message that the profile picture has been updated.
    * If the doctor does not exist, it returns a message that the doctor does not exist.
    */
    public String updateProfilePicture(int id, byte[] profilePicture) {
        if (doctorRepo.existsById(id)) {
            Doctor doctor = doctorRepo.findDoctorById(id);
            doctor.setProfilePicture(profilePicture);
            doctorRepo.save(doctor);
            return DoctorMessages.PROFILE_PICTURE_UPDATED;
        } else {
            return DoctorMessages.DOCTOR_NOT_FOUND;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting the profile picture of a doctor
    /*
    * This method gets the profile picture of a doctor by checking if the doctor exists in the database. If the doctor
    * exists, it returns the profile picture of the doctor. If the doctor does not exist, it returns null.
    */
    public byte[] getProfilePicture(int id) {
        if (doctorRepo.existsById(id)) {
            return doctorRepo.findDoctorById(id).getProfilePicture();
        } else {
            return null;
        }
    }
}

