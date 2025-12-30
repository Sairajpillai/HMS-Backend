package com.hms.appointment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.entity.ApRecord;
import java.util.List;



@Repository
public interface ApRecordRepository extends CrudRepository<ApRecord,Long> {

    Optional<ApRecord> findByAppointment_Id(Long appointment_Id);
    List<ApRecord> findByPatientId(Long patientId);
    Boolean existsByAppointment_Id(Long appoitnmentId);
}
