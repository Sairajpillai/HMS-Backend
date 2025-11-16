package com.hms.profile.repository;

import org.springframework.data.repository.CrudRepository;

import com.hms.profile.entity.Patient;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient,Long> {

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByAadharNo(String aadharNo);
    
} 
