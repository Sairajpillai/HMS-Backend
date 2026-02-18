package com.example.visitor.service.storage;

import org.springframework.stereotype.Component;

import com.example.visitor.dto.PatientResponseDTO;
import com.example.visitor.entity.Patient;

@Component
public class PatientServiceAdapter implements PatientAdapter {

    @Override
    public Patient adapt(PatientResponseDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        return patient;
    }
}
