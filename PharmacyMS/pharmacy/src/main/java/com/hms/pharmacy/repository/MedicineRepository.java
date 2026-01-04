package com.hms.pharmacy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.Medicine;
import java.util.Optional;


@Repository
public interface MedicineRepository extends CrudRepository<Medicine,Long>{
    Optional<Medicine>  findByNameIgnoreCaseAndDosageIgnoreCase(String name,String dosage);
}
