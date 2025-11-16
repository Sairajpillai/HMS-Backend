package com.hms.profile.service;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.exception.HMSUserException;

public interface DoctorService {
    public Long addDoctor(DoctorDTO doctorDTO) throws HMSUserException;
    public DoctorDTO getDoctorById(Long id) throws HMSUserException;
    public DoctorDTO updateDoctor(DoctorDTO dcotorDTO) throws HMSUserException;
}
