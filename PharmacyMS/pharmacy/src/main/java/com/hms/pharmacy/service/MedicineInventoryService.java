package com.hms.pharmacy.service;

import java.util.List;

import com.hms.pharmacy.dto.MedicineInventoryDTO;
import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.exception.HMSUserException;

public interface MedicineInventoryService {
    List<MedicineInventoryDTO> getAllMedicines() throws HMSUserException;
    MedicineInventoryDTO getMedicineById(Long id) throws HMSUserException;
    MedicineInventoryDTO addMedicine(MedicineInventoryDTO medicine) throws HMSUserException;
    MedicineInventoryDTO updateMedicine(MedicineInventoryDTO medicine) throws HMSUserException;
    void deleteMedicine(Long id) throws HMSUserException;
    void deleteExpiredMedicines() throws HMSUserException;
    // void updateMedicineStatus() throws HMSUserException;

}
