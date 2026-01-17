package com.hms.pharmacy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.MedicineInventory;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface MeidicneInventoryRepository extends CrudRepository<MedicineInventory,Long>{
    List<MedicineInventory> findByExpiryDateBefore(LocalDate expiryDate);
}
