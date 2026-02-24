package com.example.visitor.consumer;

// import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.visitor.event.VisitorVerificationEvent;
import com.example.visitor.gateway.ProfileGateway;
import com.example.visitor.repository.VisitorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorVerificationConsumer {

    private final VisitorRepository visitorRepository;
    private final ProfileGateway profileGateway;

    // @KafkaListener(topics = "visitor-verification-topic", groupId = "visitor-group")
    public void consume(VisitorVerificationEvent event) {

        log.info("Received verification event for visitorId={}", event.getVisitorId());

        try {

            var patientDTO = profileGateway.getPatient(event.getPatientId());

            var visitor = visitorRepository.findById(event.getVisitorId()).orElse(null);

            if (visitor != null && !"UNVERIFIED".equals(patientDTO.getName())) {

                visitor.markVerified();
                visitorRepository.save(visitor);

                log.info("Visitor {} verified successfully", visitor.getId());
            }

        } catch (Exception e) {
            log.error("Verification failed again. Will retry later.", e);
        }
    }
}