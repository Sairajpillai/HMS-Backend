package com.hms.pharmacy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.Sale;
import java.util.List;
import java.util.Optional;


@Repository
public interface SaleRepository extends CrudRepository<Sale,Long>{
    Boolean existsByPrescriptionId(Long prescriptionId);
    Optional<Sale> findByPrescriptionId(Long prescriptionId);
}
