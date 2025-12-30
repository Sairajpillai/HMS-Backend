package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.exception.HMSUserException;

public interface PrescriptionService {
    public Long savePrescription(PrescriptionDTO request);
    public PrescriptionDTO getPrescriptionByAppointmentId(Long appointmentId) throws HMSUserException;
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) throws HMSUserException;
    public List<PrescriptionDetails> getPrescriptionDetailsByPatientId(Long patientId) throws HMSUserException;
}
