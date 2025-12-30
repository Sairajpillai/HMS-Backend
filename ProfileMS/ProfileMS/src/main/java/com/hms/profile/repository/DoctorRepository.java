package com.hms.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.entity.Doctor;


public interface DoctorRepository extends CrudRepository<Doctor,Long> {

    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByLicenseNo(String licenseNo);

    @Query("SELECT d.id AS id,d.name AS name FROM Doctor d")
    List<DoctorDropdown> findAllDoctordropdowns();

    @Query("SELECT d.id AS id,d.name as name FROM Doctor d where d.id in ?1")
    List<DoctorDropdown> findAllDoctorDropdownsByIds(List<Long> ids); 
    
} 
