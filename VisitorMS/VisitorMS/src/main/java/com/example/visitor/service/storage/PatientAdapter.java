package com.example.visitor.service.storage;

import com.example.visitor.dto.PatientResponseDTO;
import com.example.visitor.entity.Patient;

public interface PatientAdapter {
    Patient adapt(PatientResponseDTO dto);
}
