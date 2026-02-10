package com.backend.service;

import com.backend.dto.TreatmentDto;
import com.backend.entity.Treatment;
import com.backend.entity.TreatmentID;
import com.backend.messages.TreatmentMessages;
import com.backend.repo.DoctorRepo;
import com.backend.repo.PatientRepo;
import com.backend.repo.TreatmentRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TreatmentService {

    @Autowired
    private TreatmentRepo treatmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    public TreatmentService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new PropertyMap<Treatment, TreatmentDto>() {
            @Override
            protected void configure() {
                // Explicitly map the fields to avoid conflicts
                map().setPatientId(source.getPatientId());
                map().setDoctorId(source.getDoctorId());
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for saving treatment to the database
    /*
    first, the method checks if the patient with the given ID exists in the database.
    If the patient does not exist, the method returns the message "Patient not found".
    Then, the method checks if the doctor with the given ID exists in the database.
    If the doctor does not exist, the method returns the message "Doctor not found".
    If both the patient and the doctor exist, the method saves the treatment to the
    database and returns the message "Treatment saved successfully".
    */
    public String saveTreatment( TreatmentDto treatmentDto) {
        if (patientRepo.findById(treatmentDto.getPatientId()).isEmpty()){
            return TreatmentMessages.PATIENT_NOT_FOUND;
        }
        if (doctorRepo.findById(treatmentDto.getDoctorId()).isEmpty()){
            return TreatmentMessages.DOCTOR_NOT_FOUND;
        }
        treatmentRepo.save(modelMapper.map(treatmentDto, Treatment.class));
        return TreatmentMessages.TREATMENT_SAVED_SUCCESSFULLY;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for getting all treatments from the database
    /*
    The method retrieves all treatments from the database and returns them as a list of TreatmentDto objects.
    */
    public List<TreatmentDto> getAllTreatments() {
        List<Treatment> treatments = treatmentRepo.findAll();
        return treatments.stream().map(treatment -> modelMapper.map(treatment, TreatmentDto.class)).toList();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for updating the treatment in the database
    /*
    first, the method checks if the patient with the given ID exists in the database.
    If the patient does not exist, the method returns the message "Patient not found".
    Then, the method checks if the doctor with the given ID exists in the database.
    If the doctor does not exist, the method returns the message "Doctor not found".
    If the treatment with the given patient ID and doctor ID does not exist in the database,
    the method returns the message "Treatment not found".
    If the patient, doctor, and treatment exist, the method updates the treatment in the database
    and returns the message "Treatment updated successfully".
    */
    public String updateTreatment(TreatmentDto treatmentDto) {
        if (patientRepo.findById(treatmentDto.getPatientId()).isEmpty()) {
            return TreatmentMessages.PATIENT_NOT_FOUND;
        }
        if (doctorRepo.findById(treatmentDto.getDoctorId()).isEmpty()) {
            return TreatmentMessages.DOCTOR_NOT_FOUND;
        }

        TreatmentID treatmentID = new TreatmentID(treatmentDto.getPatientId(), treatmentDto.getDoctorId());
        Optional<Treatment> existingTreatment = treatmentRepo.findById(treatmentID);

        if (existingTreatment.isEmpty()) {
            return TreatmentMessages.TREATMENT_NOT_FOUND; // Add this message in TreatmentMessages
        }

        Treatment treatment = modelMapper.map(treatmentDto, Treatment.class);
        treatmentRepo.save(treatment);

        return TreatmentMessages.TREATMENT_UPDATED_SUCCESSFULLY; // Add this message in TreatmentMessages
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method for deleting the treatment from the database
    /*
    first, the method checks if the treatment with the given patient ID and doctor ID exists in the database.
    If the treatment does not exist, the method returns the message "Treatment not found".
    If the treatment exists, the method deletes the treatment from the database and returns the
    message "Treatment deleted successfully".
    */
    public String deleteTreatment(TreatmentDto treatmentDto) {
        TreatmentID treatmentID = new TreatmentID(treatmentDto.getPatientId(), treatmentDto.getDoctorId());
        Optional<Treatment> existingTreatment = treatmentRepo.findById(treatmentID);

        if (existingTreatment.isEmpty()) {
            return TreatmentMessages.TREATMENT_NOT_FOUND;
        }

        treatmentRepo.delete(existingTreatment.get());

        return TreatmentMessages.TREATMENT_DELETED_SUCCESSFULLY;
    }

    public List<TreatmentDto> getTreatmentsByPatientId(int patientId) {
        List<Treatment> treatments = treatmentRepo.findByPatientId(patientId);
        return treatments.stream().map(treatment -> modelMapper.map(treatment, TreatmentDto.class)).toList();
    }
}
