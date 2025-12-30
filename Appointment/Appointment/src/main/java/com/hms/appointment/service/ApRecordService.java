package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.exception.HMSUserException;

public interface ApRecordService {

    public Long createApRecord(ApRecordDTO request) throws HMSUserException;
    public void updateApRecord(ApRecordDTO request) throws HMSUserException;
    public ApRecordDTO getApRecordByappointmentId(Long appointmentId) throws HMSUserException;
    public ApRecordDTO getApRecordDetailsByAppointmentId(long appointmentId) throws HMSUserException;
    public ApRecordDTO getApRecordById(Long recordId) throws HMSUserException;
    public List<RecordDetails> getApRecordsByPatientId(Long patientId) throws HMSUserException;
    public boolean isRecordExists(Long appointmentId) throws HMSUserException;
}
