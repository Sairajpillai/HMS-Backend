package com.example.visitor.service;

import java.util.List;

import com.example.visitor.dto.VisitorDTO;
import com.example.visitor.exception.HMSUserException;

public interface VisitorService {
    public VisitorDTO saveVisitor(VisitorDTO visitorDTO) throws HMSUserException; 
    public VisitorDTO getVisitorById(Long id);
    public List<VisitorDTO> getVisitorByPatientID(Long PatientId);
    public VisitorDTO updateVisitor(VisitorDTO visitorDTO);
}
