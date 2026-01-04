package com.hms.pharmacy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.MedicineInventory;

@Repository
public interface MeidicneInventoryRepository extends CrudRepository<MedicineInventory,Long>{
    
}
