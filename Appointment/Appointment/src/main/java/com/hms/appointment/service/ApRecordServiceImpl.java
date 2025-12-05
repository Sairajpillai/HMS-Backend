package com.hms.appointment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.entity.ApRecord;
import com.hms.appointment.exception.HMSUserException;
import com.hms.appointment.repository.ApRecordRepository;
import com.hms.appointment.utility.StringListConverter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApRecordServiceImpl implements ApRecordService{

    private final ApRecordRepository apRecordRepository;
    private final PrescriptionService prescriptionService;

    @Override
    public Long createApRecord(ApRecordDTO request) throws HMSUserException {
        Optional<ApRecord>  existingRecord = apRecordRepository.findByAppointment_Id(request.getAppointmentId());
        if(existingRecord.isPresent()){
            throw new HMSUserException("APPOINTMENT_RECORD_ALREADY_EXIST");
        }
        Long id =  apRecordRepository.save(request.toEntity()).getId();
        if(request.getPrescription() != null){
            request.getPrescription().setAppointmentId(request.getAppointmentId());
            prescriptionService.savePrescription(request.getPrescription());
        }
        return id;
    }

    @Override
    public void updateApRecord(ApRecordDTO request) throws HMSUserException {
        ApRecord existingRecord = apRecordRepository.findById(request.getId()).orElseThrow(()-> new HMSUserException("APPOINTMENT_RECORD_NOT_FOUND"));
        existingRecord.setNotes(request.getNotes());
        existingRecord.setDiagnosis(request.getDiagnosis());
        existingRecord.setFollowUpDate(request.getFollowUpDate());
        existingRecord.setSymptoms(StringListConverter.convertListToString(request.getSymptoms()));
        existingRecord.setReferral(request.getReferral());
        apRecordRepository.save(existingRecord);
    }

    @Override
    public ApRecordDTO getApRecordByappointmentId(Long appointmentId) throws HMSUserException {
        return apRecordRepository.findByAppointment_Id(appointmentId).orElseThrow(()-> new HMSUserException("APPOINTMENT_RECORD_NOT_FOUND")).toDTO();
    }
    
    @Override
    public ApRecordDTO getApRecordById(Long recordId) throws HMSUserException {
        return apRecordRepository.findById(recordId).orElseThrow(()-> new HMSUserException("APPOINTMENT_RECORD_NOT_FOUND")).toDTO();
    }

    @Override
    public ApRecordDTO getApRecordDetailsByAppointmentId(long appointmentId) throws HMSUserException {
                ApRecordDTO record =  apRecordRepository.findByAppointment_Id(appointmentId).orElseThrow(()-> new HMSUserException("APPOINTMENT_RECORD_NOT_FOUND")).toDTO();
                record.setPrescription(prescriptionService.getPrescriptionByAppointmentId(appointmentId));
                return record;
    }
    
}
