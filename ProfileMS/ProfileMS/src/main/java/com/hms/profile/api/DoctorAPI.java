package com.hms.profile.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.exception.HMSUserException;
import com.hms.profile.service.DoctorService;

@RestController
@CrossOrigin
@RequestMapping("/profile/doctor")
@Validated
public class DoctorAPI {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<Long> addDoctor(@RequestBody DoctorDTO doctorDTO) throws HMSUserException {
        return new ResponseEntity<>(doctorService.addDoctor(doctorDTO),HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) throws HMSUserException {
        return new ResponseEntity<>(doctorService.getDoctorById(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO dcotorDTO) throws HMSUserException{
        return new ResponseEntity<>(doctorService.updateDoctor(dcotorDTO),HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> doctorExists(@PathVariable Long id) throws HMSUserException{
        return new ResponseEntity<>(doctorService.doctorExists(id),HttpStatus.OK);
    } 

    @GetMapping("/dropdowns")
    public ResponseEntity<List<DoctorDropdown>> getDoctorDropdowns() throws HMSUserException{
        return new ResponseEntity<>(doctorService.getDoctorDropdowns(),HttpStatus.OK);
    }
    
}
