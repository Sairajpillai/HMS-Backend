package com.hms.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.dto.DoctorDTO;
import com.hms.appointment.dto.PatientDTO;
import com.hms.appointment.dto.Status;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.exception.HMSUserException;
import com.hms.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ApiService apiService;

    @Override
    public Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HMSUserException {
        Boolean doctorExist = apiService.doctorExist(appointmentDTO.getDoctorId()).block();
        if (doctorExist == null || !doctorExist) {
            throw new HMSUserException("DOCTOR_NOT_FOUND");
        }
        Boolean patientExist = apiService.patientExist(appointmentDTO.getPatientId()).block();
        if (patientExist == null || !doctorExist) {
            throw new HMSUserException("PATIENT_NOT_FOUND");
        }
        appointmentDTO.setStatus(Status.SCHEDULED);
        return appointmentRepository.save(appointmentDTO.toEntity()).getId();
    }

    @Override
    public void cancelAppointment(Long appointmentId) throws HMSUserException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HMSUserException("APPOINTMENT_NOT_FOUND"));
        if (appointment.getStatus().equals(Status.CANCELLED)) {
            throw new HMSUserException("APPOINTMENT_ALREADY_CANCELLED");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);

    }

    @Override
    public void completeAppointment(Long appointmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeAppointment'");
    }

    @Override
    public void rescheduleAppointment(Long appointmentId, String newDatetime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rescheduleAppointment'");
    }

    @Override
    public AppointmentDTO getappointmentDetails(Long appointmentId) throws HMSUserException {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HMSUserException("APPOINTMENT_NOT_FOUND")).toDTO();
    }

    @Override
    public AppointmentDetails getAppointmentDetailsWithName(Long appointmentId) throws HMSUserException {
        AppointmentDTO appointmentDTO = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HMSUserException("APPOINTMENT_NOT_FOUND")).toDTO();
        DoctorDTO doctorDTO = apiService.getDoctorByID(appointmentDTO.getDoctorId()).block();
        PatientDTO patientDTO = apiService.getPatientByID(appointmentDTO.getPatientId()).block();
        return new AppointmentDetails(appointmentDTO.getId(), appointmentDTO.getPatientId(), patientDTO.getName(),patientDTO.getEmail(),patientDTO.getPhone(),
                appointmentDTO.getDoctorId(), doctorDTO.getName(), appointmentDTO.getAppointmentTime(),
                appointmentDTO.getStatus(), appointmentDTO.getReason(), appointmentDTO.getNotes());
    }

   

}
