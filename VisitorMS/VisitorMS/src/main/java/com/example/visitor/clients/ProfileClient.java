package com.example.visitor.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.visitor.dto.PatientResponseDTO;


@FeignClient(name="ProfileMS")
public interface ProfileClient {

    @GetMapping("/profile/patient/exists/{id}")
    Boolean patientExist(@PathVariable("id") Long id);

    @GetMapping("/profile/patient/get/{id}")
    PatientResponseDTO getPatientById(@PathVariable("id") Long id);

}
