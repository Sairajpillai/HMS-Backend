package com.example.visitor.entity.builder;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.visitor.entity.Visitor;

public class VisitorBuilder {

    private Long patientId;
    private String patientName;
    private String visitorName;
    private String relation;
    private String purpose;
    private LocalDate visitDate;
    private LocalTime inTime;
    private LocalTime outTime;
    private String createdBy;
    private String verificationStatus;

    public VisitorBuilder patient(Long id, String name) {
        this.patientId = id;
        this.patientName = name;
        return this;
    }

    public VisitorBuilder visitorDetails(String name, String relation) {
        this.visitorName = name;
        this.relation = relation;
        return this;
    }

    public VisitorBuilder purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public VisitorBuilder visitTiming(LocalTime in) {
        this.inTime = in;
        return this;
    }

    public VisitorBuilder createdBy(String user) {
        this.createdBy = user;
        return this;
    }

    public VisitorBuilder verificationStatus(String status) {
        this.verificationStatus = status;
        return this;
    }

    public Visitor build() {

        if (patientId == null) {
            throw new IllegalStateException("Patient is required");
        }

        if (visitorName == null) {
            throw new IllegalStateException("Visitor name is required");
        }
        return new Visitor(
                null,
                patientId,
                patientName,
                visitorName,
                relation,
                purpose,
                LocalDate.now(),
                inTime,
                outTime,
                createdBy,
                verificationStatus);
    }
}
