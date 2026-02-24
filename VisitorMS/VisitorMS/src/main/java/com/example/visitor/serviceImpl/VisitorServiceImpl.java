package com.example.visitor.serviceImpl;

import java.time.LocalTime;
import java.util.List;

// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.visitor.clients.ProfileClient;
import com.example.visitor.dto.PatientResponseDTO;
import com.example.visitor.dto.VisitorDTO;
import com.example.visitor.entity.Patient;
import com.example.visitor.entity.Visitor;
import com.example.visitor.entity.builder.VisitorBuilder;
import com.example.visitor.event.VisitorVerificationEvent;
import com.example.visitor.exception.HMSUserException;
import com.example.visitor.gateway.ProfileGateway;
import com.example.visitor.repository.VisitorRepository;
import com.example.visitor.service.VisitorService;
import com.example.visitor.service.storage.PatientServiceAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepo;

    private final ProfileClient profileClient;

    private final PatientServiceAdapter patientAdapter;

    private final ProfileGateway profileGateway;

    // private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public VisitorDTO saveVisitor(VisitorDTO visitorDTO) throws HMSUserException {

        Patient patient;

        // direct calling ProfileMS through feing client
        // if (!profileClient.patientExist(visitorDTO.getPatientId())) {
        //     throw new HMSUserException("PATIENT_NOT_FOUND");
        // } else {
        //     PatientResponseDTO patientDTO = profileClient.getPatientById(visitorDTO.getPatientId());
        //     patient = patientAdapter.adapt(patientDTO);
        // }

        PatientResponseDTO patientDTO = profileGateway.getPatient(visitorDTO.getPatientId());
        patient = patientAdapter.adapt(patientDTO);

        String verificationStatus ="UNVERIFIED".equals(patient.getName())? "PENDING_VERIFICATION": "VERIFIED";

        Visitor visitor = new VisitorBuilder()
                .patient(patient.getId(), patient.getName())
                .purpose(visitorDTO.getPurpose())
                .visitTiming(LocalTime.now())
                .visitorDetails(visitorDTO.getVisitorName(), visitorDTO.getRelation())
                .verificationStatus(verificationStatus)
                .build();

        Visitor savedVisitor = visitorRepo.save(visitor);

        // Kafka
        // if ("PENDING_VERIFICATION".equals(savedVisitor.getVerificationStatus())) {

        //     VisitorVerificationEvent event =
        //             new VisitorVerificationEvent(
        //                     savedVisitor.getId(),
        //                     savedVisitor.getPatientId()
        //             );

        //     kafkaTemplate.send("visitor-verification-topic", event);
        // }

        return VisitorDTO.builder().id(savedVisitor.getId())
                .patientId(savedVisitor.getPatientId())
                .patientName(savedVisitor.getPatientName())
                .visitorName(savedVisitor.getVisitorName())
                .relation(savedVisitor.getRelation())
                .purpose(savedVisitor.getPurpose())
                .visitDate(savedVisitor.getVisitDate())
                .inTime(savedVisitor.getInTime())
                .build();
        // new VisitorBuilder().
        // visitorRepo.save(visitorDTO);
        // return null;
    }

    @Override
    public VisitorDTO getVisitorById(Long id) {

        return null;
    }

    @Override
    public List<VisitorDTO> getVisitorByPatientID(Long PatientId) {
        return null;

    }

    @Override
    public VisitorDTO updateVisitor(VisitorDTO visitorDTO) {
        return null;

    }

}
