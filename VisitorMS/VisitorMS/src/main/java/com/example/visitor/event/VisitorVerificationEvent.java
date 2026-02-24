package com.example.visitor.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorVerificationEvent {

    private Long visitorId;
    private Long patientId;
}