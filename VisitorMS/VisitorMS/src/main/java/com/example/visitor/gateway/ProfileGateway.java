package com.example.visitor.gateway;

import org.springframework.stereotype.Component;

import com.example.visitor.clients.ProfileClient;
import com.example.visitor.dto.PatientResponseDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileGateway {

    private final ProfileClient profileClient;

    @Retry(name = "profileService")
    @CircuitBreaker(name = "profileService", fallbackMethod = "getPatientFallback")
    public PatientResponseDTO getPatient(Long patientId) {
        log.info("Calling ProfileMS for patientId={}", patientId);
        return profileClient.getPatientById(patientId);
    }

    public PatientResponseDTO getPatientFallback(Long patientId, Throwable ex) {

        log.error("ProfileMS DOWN. Using fallback for patientId={}", patientId, ex);
        System.out.println("ProfileMS DOWN. Using fallback for patientId={}"+ patientId +" Error "+ ex);

        PatientResponseDTO fallback = new PatientResponseDTO();
        fallback.setId(patientId);
        fallback.setName("UNVERIFIED");

        return fallback;
    }
}