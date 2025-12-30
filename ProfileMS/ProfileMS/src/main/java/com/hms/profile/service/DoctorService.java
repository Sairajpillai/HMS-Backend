package com.hms.profile.service;

import java.util.List;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.exception.HMSUserException;

public interface DoctorService {
    public Long addDoctor(DoctorDTO doctorDTO) throws HMSUserException;
    public DoctorDTO getDoctorById(Long id) throws HMSUserException;
    public DoctorDTO updateDoctor(DoctorDTO dcotorDTO) throws HMSUserException;
    public Boolean doctorExists(Long id) throws HMSUserException;
    public List<DoctorDropdown> getDoctorDropdowns() throws HMSUserException;
    public List<DoctorDropdown> getDoctorsById(List<Long> ids) throws HMSUserException;
}
