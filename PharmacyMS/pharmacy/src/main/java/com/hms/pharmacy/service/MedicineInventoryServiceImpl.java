package com.hms.pharmacy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.pharmacy.dto.MedicineInventoryDTO;
import com.hms.pharmacy.dto.StockStatus;
import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.exception.HMSUserException;
import com.hms.pharmacy.repository.MeidicneInventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineInventoryServiceImpl implements MedicineInventoryService {

    private final MeidicneInventoryRepository medicineInventoryRepository;
    private final MedicineService medicineService;

    @Override
    public List<MedicineInventoryDTO> getAllMedicines() throws HMSUserException {
        List<MedicineInventory> medicineInventories = (List<MedicineInventory>) medicineInventoryRepository.findAll();
        return medicineInventories.stream().map(MedicineInventory::toDTO).toList();
    }

    @Override
    public MedicineInventoryDTO getMedicineById(Long id) throws HMSUserException {
        return medicineInventoryRepository.findById(id).orElseThrow(() -> new HMSUserException("INVENTORY_NOT_FOUND"))
                .toDTO();
    }

    @Override
    public MedicineInventoryDTO addMedicine(MedicineInventoryDTO medicine) throws HMSUserException {
        medicine.setAddedDate(LocalDate.now());
        medicine.setInitialQuantity(medicine.getQuantity());
        medicine.setStatus(StockStatus.ACTIVE);
        medicineService.addStock(medicine.getMedicineId(), medicine.getQuantity());
        return medicineInventoryRepository.save(medicine.toEntity()).toDTO();
    }

    @Override
    public MedicineInventoryDTO updateMedicine(MedicineInventoryDTO medicine) throws HMSUserException {
        MedicineInventory medicineInventory = medicineInventoryRepository.findById(medicine.getId())
                .orElseThrow(() -> new HMSUserException("INVENTORY_NOT_FOUND"));
        medicineInventory.setBatchNo(medicine.getBatchNo());

        if (medicineInventory.getQuantity() < medicine.getQuantity()) {
            medicineService.addStock(medicine.getMedicineId(),
                    medicine.getQuantity() - medicineInventory.getQuantity());
        } else if (medicineInventory.getQuantity() > medicine.getQuantity()) {
            medicineService.removeStock(medicine.getMedicineId(),
                    medicineInventory.getQuantity() - medicine.getQuantity());
        }
        medicineInventory.setQuantity(medicine.getQuantity());
        medicineInventory.setExpiryDate(medicine.getExpiryDate());
        medicineInventory.setInitialQuantity(medicine.getQuantity());
        return medicineInventoryRepository.save(medicineInventory).toDTO();
    }

    @Override
    public void deleteMedicine(Long id) throws HMSUserException {
        medicineInventoryRepository.deleteById(id);
    }

    private void markExpired(List<MedicineInventory> inventories) throws HMSUserException {
        for (MedicineInventory inventory : inventories) {
            inventory.setStatus(StockStatus.EXPIRED);
        }
        medicineInventoryRepository.saveAll(inventories);
    }

    @Override
    // @Scheduled(cron = "0 09 00 * * ?")
    public void deleteExpiredMedicines() throws HMSUserException {
        System.out.println("deleting expired mediccines....");
        List<MedicineInventory> expiredMedicines = medicineInventoryRepository.findByExpiryDateBefore(LocalDate.now());
        for (MedicineInventory medicine : expiredMedicines) {
            medicineService.removeStock(medicine.getMedicine().getId(), medicine.getQuantity());
        }
        this.markExpired(expiredMedicines);
    }

    // @Scheduled(fixedRate = 5000)
    // public void print(){
    // System.out.println("Scheduled task running....");
    // }

    // @Scheduled(cron = "0 39 23 * * ?")
    // public void print2(){
    // System.out.println("Scheduled task running....");
    // }

}
