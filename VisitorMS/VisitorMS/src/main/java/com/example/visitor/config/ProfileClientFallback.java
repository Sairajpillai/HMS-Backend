package com.example.visitor.config;

import org.springframework.stereotype.Component;

import com.example.visitor.clients.ProfileClient;
import com.example.visitor.dto.PatientResponseDTO;

@Component
public class ProfileClientFallback implements ProfileClient {

    @Override
    public PatientResponseDTO getPatientById(Long id) {

        return new PatientResponseDTO(
                id,
                "Service Unavailable",
                "Patient MS Down",
                null, "N/A", null, null, null, null, null
        );
    }

    @Override
    public Boolean patientExist(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patientExist'");
    }
}