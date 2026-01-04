package com.hms.pharmacy.service;

import java.util.List;

import com.hms.pharmacy.dto.MedicineDTO;
import com.hms.pharmacy.exception.HMSUserException;

public interface MedicineService {
    public Long addMedicine(MedicineDTO medicineDTO) throws HMSUserException;
    public MedicineDTO getMedicineById(Long id) throws HMSUserException;
    public void updateMedicine(MedicineDTO medicineDTO) throws HMSUserException;
    public List<MedicineDTO> getAllMedicines() throws HMSUserException;
}
