package com.hms.appointment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.RecordDetails;
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
    private final ProfileClient profileClient;

    @Override
    public Long createApRecord(ApRecordDTO request) throws HMSUserException {
        Optional<ApRecord>  existingRecord = apRecordRepository.findByAppointment_Id(request.getAppointmentId());
        if(existingRecord.isPresent()){
            throw new HMSUserException("APPOINTMENT_RECORD_ALREADY_EXIST");
        }
        request.setCreatedAt(LocalDateTime.now());
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

    @Override
    public List<RecordDetails> getApRecordsByPatientId(Long patientId) throws HMSUserException {
        List<ApRecord> records = apRecordRepository.findByPatientId(patientId);
        List<RecordDetails> recordDetails = records.stream().map(ApRecord::toRecordDetails).toList();
        List<Long> doctorIds = recordDetails.stream().map(RecordDetails::getDoctorId).distinct().toList();
        List<DoctorName> doctors = profileClient.getDoctorById(doctorIds);
        Map<Long,String> doctorMap = doctors.stream().collect(Collectors.toMap(DoctorName::getId,DoctorName::getName));
        recordDetails.forEach(record -> {
            String doctorName = doctorMap.get(record.getDoctorId());
            if(doctorName != null){
                record.setDoctorName(doctorName);
            }else{
                record.setDoctorName("Unknown Doctor");
            }
        });

        return recordDetails;
    }

    @Override
    public boolean isRecordExists(Long appointmentId) throws HMSUserException {
        return apRecordRepository.existsByAppointment_Id(appointmentId);
    }
    
}
