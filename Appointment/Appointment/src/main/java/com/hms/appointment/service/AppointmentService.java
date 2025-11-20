package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.exception.HMSUserException;

public interface AppointmentService {
    public Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HMSUserException;

    void cancelAppointment(Long appointmentId) throws HMSUserException;

    void completeAppointment(Long appointmentId);

    void rescheduleAppointment(Long appointmentId,String newDatetime);

    AppointmentDTO getappointmentDetails(Long appointmentId) throws HMSUserException;

    AppointmentDetails getAppointmentDetailsWithName(Long appointmentId) throws HMSUserException;
}
