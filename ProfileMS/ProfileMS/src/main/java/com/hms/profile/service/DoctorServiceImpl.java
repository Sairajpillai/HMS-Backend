package com.hms.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.exception.HMSUserException;
import com.hms.profile.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Long addDoctor(DoctorDTO doctorDTO) throws HMSUserException {
        if(doctorDTO.getEmail() != null && doctorRepository.findByEmail(doctorDTO.getEmail()).isPresent()) throw new HMSUserException("DOCTOR_ALREADY_EXIST");
        if(doctorDTO.getLicenseNo() != null && doctorRepository.findByLicenseNo(doctorDTO.getLicenseNo()).isPresent()) throw new HMSUserException("DOCTOR_ALREADY_EXIST");
        return doctorRepository.save(doctorDTO.toEntity()).getId();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) throws HMSUserException {
        return doctorRepository.findById(id).orElseThrow(() -> new HMSUserException("DOCTOR_NOT_FOUND")).toDTO();
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO dcotorDTO) throws HMSUserException {
        if(!doctorRepository.findById(dcotorDTO.getId()).isPresent()){
            throw new HMSUserException("DOCTOR_NOT_FOUND");
        }

        return doctorRepository.save(dcotorDTO.toEntity()).toDTO();

    }

    @Override
    public Boolean doctorExists(Long id) throws HMSUserException {
        return doctorRepository.existsById(id);
    }
    
}
