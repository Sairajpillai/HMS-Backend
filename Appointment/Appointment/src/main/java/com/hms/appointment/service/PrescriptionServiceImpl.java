package com.hms.appointment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.entity.Prescription;
import com.hms.appointment.exception.HMSUserException;
import com.hms.appointment.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService{
    
    private final PrescriptionRepository prescriptionRepository;

    private final MedicineService medicineService;

    private final ProfileClient profileClient;

    @Override
    public Long savePrescription(PrescriptionDTO request) {
        request.setPrescriptionDate(LocalDate.now());
       Long prescriptionId = prescriptionRepository.save(request.toEntity()).getId();
       request.getMedicines().forEach(medicine -> {
        medicine.setPrescriptionId(prescriptionId);
       });
       medicineService.saveAllMedicines(request.getMedicines());
       return prescriptionId;
    }

    @Override
    public PrescriptionDTO getPrescriptionByAppointmentId(Long appointmentId) throws HMSUserException {
        PrescriptionDTO prescriptionDTO = prescriptionRepository.findByAppointment_Id(appointmentId).orElseThrow(()-> new HMSUserException("PRESCRIPTION_NOT_FOUND")).toDTO();
        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;
    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) throws HMSUserException {
        PrescriptionDTO prescriptionDTO =  prescriptionRepository.findById(prescriptionId).orElseThrow(() ->  new HMSUserException("PRESCRIPTION_NOT_FOUND")).toDTO();
        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;
    }

    @Override
    public List<PrescriptionDetails> getPrescriptionDetailsByPatientId(Long patientId) throws HMSUserException {
        List<Prescription> prescriptions = prescriptionRepository.findAllByPatientId(patientId);
        List<PrescriptionDetails> prescriptionDetails = prescriptions.stream().map(Prescription::toDetails).toList();
        prescriptionDetails.forEach(details ->{
            details.setMedicines(medicineService.getAllMedicinesByPrescriptionId(details.getId()));
        });
        List<Long> doctorIds = prescriptionDetails.stream().map(PrescriptionDetails::getDoctorId).distinct().toList();
        List<DoctorName> doctorNames = profileClient.getDoctorById(doctorIds);
        Map<Long,String> doctorMap = doctorNames.stream().collect(java.util.stream.Collectors.toMap(DoctorName::getId,DoctorName::getName));
        prescriptionDetails.forEach(details -> {
            String doctorName = doctorMap.get(details.getDoctorId());
            if(doctorName != null){
                details.setDoctorName(doctorName);
            }else{
                details.setDoctorName("Unknown Doctor");
            }
        });

        return prescriptionDetails;
    }
}
