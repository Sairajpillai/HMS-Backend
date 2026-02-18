package com.example.visitor.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VisitorDTO {
    private Long id;

    private Long patientId;
    private String patientName;

    private String visitorName;
    private String relation;
    private String purpose;

    private LocalDate visitDate;
    private LocalTime inTime;
    private LocalTime outTime;

    private String createdBy;
}
