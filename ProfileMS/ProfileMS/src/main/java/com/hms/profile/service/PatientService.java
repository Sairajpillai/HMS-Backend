package com.hms.profile.service;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.exception.HMSUserException;

public interface PatientService {
    public Long addPatient(PatientDTO patientDTO) throws HMSUserException;
    public PatientDTO getPatientById(Long id) throws HMSUserException;
    public PatientDTO updatePatient(PatientDTO patientDTO) throws HMSUserException;
    public Boolean patientExists(Long id);
}
