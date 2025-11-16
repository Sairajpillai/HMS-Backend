package com.hms.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.exception.HMSUserException;
import com.hms.profile.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Long addPatient(PatientDTO patientDTO) throws HMSUserException {
        if(patientDTO.getEmail() != null && patientRepository.findByEmail(patientDTO.getEmail()).isPresent()) throw new HMSUserException("PATIENT_ALREADY_EXIST");
        if(patientDTO.getAadharNo() != null && patientRepository.findByAadharNo(patientDTO.getAadharNo()).isPresent()) throw new HMSUserException("PATIENT_ALREADY_EXIST");
        return patientRepository.save(patientDTO.toEntity()).getId();
    }

    @Override
    public PatientDTO getPatientById(Long id) throws HMSUserException {
        return patientRepository.findById(id).orElseThrow(() -> new HMSUserException("PATIENT_NOT_FOUND")).toDTO();
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) throws HMSUserException{
        if(!patientRepository.findById(patientDTO.getId()).isPresent()){
            throw new HMSUserException("PATIENT_NOT_FOUND");
        }

        return patientRepository.save(patientDTO.toEntity()).toDTO();
    }
    
}
