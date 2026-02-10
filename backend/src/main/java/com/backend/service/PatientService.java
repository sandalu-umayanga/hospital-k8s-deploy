package com.backend.service;

import com.backend.dto.DoctorDto;
import com.backend.dto.LoginDto;
import com.backend.dto.PatientDto;
import com.backend.entity.Doctor;
import com.backend.entity.Patient;
import com.backend.messages.PatientMessages;
import com.backend.repo.AttendantRepo;
import com.backend.repo.DoctorRepo;
import com.backend.repo.NurseRepo;
import com.backend.repo.PatientRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NurseRepo nurseRepo;
    @Autowired
    private AttendantRepo attendantRepo;
    @Autowired
    private DoctorRepo doctorRepo;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving patient to the database
    /*
    when a patient is saved, the method checks if the patient already exists in the database or not and
    if the nurse, attendant, and doctors exist in the database or not. If the patient already exists, the method
    returns a message that the patient already exists. If the nurse, attendant, or doctor does not exist, the method
    returns a message that the nurse, attendant, or doctor does not exist. If the patient does not exist and the nurse,
    attendant, and doctors exist, the patient is saved to the database and a message is returned that the patient is saved.
    */
    public String savePatient(PatientDto patientDto) {
        if (patientRepo.findPatientByDetails(patientDto.getFirstName(), patientDto.getLastName(), patientDto.getDob(), patientDto.getAddress(), patientDto.getPhone()) != null){
            return PatientMessages.PATIENT_ALREADY_EXISTS;
        } else {
            Patient patient = modelMapper.map(patientDto, Patient.class);

            // encode the password
            patient.setPassword(passwordEncoder.encode(patientDto.getPassword()));

            // check if the nurse, attendant, and doctors exist in the database
            if (patientDto.getNurse() != null) {
                if (!nurseRepo.existsById(patientDto.getNurse().getId())) {
                    return PatientMessages.NURSE_NOT_FOUND;
                }
            }
            if (patientDto.getAttendant() != null) {
                if (!attendantRepo.existsById(patientDto.getAttendant().getId())) {
                    return PatientMessages.ATTENDANT_NOT_FOUND;
                }
            }
            if(patientDto.getDoctorsIDs() != null) {
                List<Integer> doctorIds = patientDto.getDoctorsIDs();
                for (int id : doctorIds) {
                    if (!doctorRepo.existsById(id)) {
                        return PatientMessages.DOCTOR_NOT_FOUND;
                    }
                }
                List<Doctor> doctors = doctorIds.stream()
                        .map(id -> doctorRepo.findById(id).orElse(null))
                        .filter(doctor -> doctor != null)
                        .collect(Collectors.toList());
                patient.setDoctors(doctors);

            }
            patientRepo.save(patient);
            return PatientMessages.PATIENT_SAVED;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating an existing patient
    /*
    when a patient is updated, the method checks if the patient exists in the database or not and
    if the nurse, attendant, and doctors exist in the database or not. If the patient does not exist, the method
    returns a message that the patient does not exist. If the nurse, attendant, or doctor does not exist, the method
    returns a message that the nurse, attendant, or doctor does not exist. If the patient exists and the nurse,
    attendant, and doctors exist, the patient is updated in the database and a message is returned that the patient is updated.
    */
    public String updatePatient(PatientDto patientDto) {
        if (patientRepo.existsById(patientDto.getId())) {
            Patient patient = modelMapper.map(patientDto, Patient.class);

            // encode the password
            if(patientDto.getPassword() != null) {
                patient.setPassword(passwordEncoder.encode(patientDto.getPassword()));
            }

            // check if the nurse, attendant, and doctors exist in the database
            if (patientDto.getNurse() != null) {
                if (!nurseRepo.existsById(patientDto.getNurse().getId())) {
                    return PatientMessages.NURSE_NOT_FOUND;
                }
            }
            if (patientDto.getAttendant() != null) {
                if (!attendantRepo.existsById(patientDto.getAttendant().getId())) {
                    return PatientMessages.ATTENDANT_NOT_FOUND;
                }
            }
            if(patientDto.getDoctorsIDs() != null) {
                List<Integer> doctorIds = patientDto.getDoctorsIDs();
                for (int id : doctorIds) {
                    if (!doctorRepo.existsById(id)) {
                        return PatientMessages.DOCTOR_NOT_FOUND;
                    }
                }
                List<Doctor> doctors = doctorIds.stream()
                        .map(id -> doctorRepo.findById(id).orElse(null))
                        .filter(doctor -> doctor != null)
                        .collect(Collectors.toList());
                patient.setDoctors(doctors);

            }
            patientRepo.save(patient);
            return PatientMessages.PATIENT_UPDATED;
        } else {
            return PatientMessages.PATIENT_NOT_FOUND;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting an existing patient
    /*
    when a patient is deleted, the method checks if the patient exists in the database or not. If the patient does not exist,
    the method returns a message that the patient does not exist. If the patient exists, the patient is deleted from the database
    and a message is returned that the patient is deleted and other related entities also updated accordingly.
    */
    public String deletePatient(PatientDto patientDto) {
        if(patientRepo.existsById(patientDto.getId())) {
            patientRepo.delete(modelMapper.map(patientDto, Patient.class));
            return PatientMessages.PATIENT_DELETED;
        } else {
            return PatientMessages.PATIENT_NOT_FOUND;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a patient by id
    /*
    when a patient is found by id, the method checks if the patient exists in the database or not. If the patient exists,
    the patient is returned. If the patient does not exist, null is returned.
    */
    public PatientDto findPatientById(int id) {
        Optional<Patient> patient = patientRepo.findById(id);
        if (patient.isPresent()) {
            return modelMapper.map(patient.get(), PatientDto.class);
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //method for getting doctor list of a patient
    /*
    when the doctor list of a patient is found, the method checks if the patient exists in the database or not. If the patient exists,
    the doctor list of the patient is returned. If the patient does not exist, null is returned.
    */
    public List<DoctorDto> getDoctorList(int id) {
        if (patientRepo.existsById(id)) {
            return patientRepo.findPatientById(id).getDoctors().stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class)).toList();
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for delete patient by ID
    /*
    when a patient is deleted by id, the method checks if the patient exists in the database or not. If the patient does not exist,
    the method returns a message that the patient does not exist. If the patient exists, the patient is deleted from the database
    */
    public String deletePatientById(int id) {
        if(patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
            return PatientMessages.PATIENT_DELETED;
        } else {
            return PatientMessages.PATIENT_NOT_FOUND;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all patients from the database
    /*
    when all patients are found, the method returns a list of all patients in the database.
    */
    public List<PatientDto> getAllPatients() {
        List<Patient> patientList = patientRepo.findAll();
        return patientList.stream().map(patient -> modelMapper.map(patient, PatientDto.class)).toList();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for finding a patient by name
    /*
    when a patient is found by name, the method checks if the patient exists in the database or not. If the patient exists,
    the patient is returned. If the patient does not exist, null is returned. (if there is more than one patient with the
    same name, all patients with the same name are returned)
    */
    public List<PatientDto> findPatientByName(String name) {
        List<Patient> patientList = patientRepo.findPatientByName(name);
        return patientList.stream().map(patient -> modelMapper.map(patient, PatientDto.class)).toList();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for login using email and password
    /*
    when a patient logs in, the method checks if the patient exists in the database or not. If the patient exists,
    the method checks if the password is correct or not. If the password is correct, the patient is logged in and
    a message is returned that the patient is logged in. If the password is incorrect, a message is returned that the
    password is incorrect. If the patient does not exist, a message is returned that the patient does not exist.
    */
    public String loginPatient( LoginDto loginDto) {
        Patient patient = patientRepo.findPatientByEmail(loginDto.getEmail());
        if (patient != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), patient.getPassword())) {
                return PatientMessages.PATIENT_LOGGED_IN;
            } else {
                return PatientMessages.PASSWORD_INCORRECT;
            }
        } else {
            return PatientMessages.PATIENT_NOT_FOUND;
        }
    }
}
