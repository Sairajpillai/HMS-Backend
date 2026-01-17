package com.hms.pharmacy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.MedicineDTO;
import com.hms.pharmacy.entity.Medicine;
import com.hms.pharmacy.exception.HMSUserException;
import com.hms.pharmacy.repository.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    @Override
    public Long addMedicine(MedicineDTO medicineDTO) throws HMSUserException {
        Optional<Medicine> optional = medicineRepository.findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(),
                medicineDTO.getDosage());
        if (optional.isPresent()) {
            throw new HMSUserException("MEDICINE_ALREADY_EXIST");
        }
        medicineDTO.setCreatedAt(LocalDateTime.now());
        medicineDTO.setStock(0);
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public MedicineDTO getMedicineById(Long id) throws HMSUserException {
        return medicineRepository.findById(id).orElseThrow(() -> new HMSUserException("MEDICINE_NOT_FOUND")).toDTO();
    }

    @Override
    public void updateMedicine(MedicineDTO medicineDTO) throws HMSUserException {
        Medicine existingMedicine = medicineRepository.findById(medicineDTO.getId())
                .orElseThrow(() -> new HMSUserException("MEDICINE_NOT_FOUND"));
        if (!(medicineDTO.getName().equalsIgnoreCase(existingMedicine.getName())
                && medicineDTO.getDosage().equalsIgnoreCase(existingMedicine.getDosage()))) {
            Optional<Medicine> optional = medicineRepository
                    .findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(), medicineDTO.getDosage());
            if (optional.isPresent()) {
                throw new HMSUserException("MEDICINE_ALREADY_EXISTS");
            }
        }
        existingMedicine.setName(medicineDTO.getName());
        existingMedicine.setDosage(medicineDTO.getDosage());
        existingMedicine.setCategory(medicineDTO.getCategory());
        existingMedicine.setType(medicineDTO.getType());
        existingMedicine.setManufacturer(medicineDTO.getManufacturer());
        existingMedicine.setUnitPrice(medicineDTO.getUnitPrice());
        // existingMedicine.setCreatedAt(medicineDTO.getCreatedAt());
        medicineRepository.save(existingMedicine);
    }

    @Override
    public List<MedicineDTO> getAllMedicines() throws HMSUserException {
        return ((List<Medicine>)medicineRepository.findAll()).stream().map(Medicine::toDTO).toList();
    }

    @Override
    public Integer getStockById(Long id) throws HMSUserException {
        return medicineRepository.findStockById(id).orElseThrow(()->new HMSUserException("MEDICINE_NOT_FOUND"));
    }

    @Override
    public Integer addStock(Long id, Integer quantity) throws HMSUserException {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(()->new HMSUserException("MEDICINE_NOT_FOUND"));
        medicine.setStock(medicine.getStock() != null ? medicine.getStock() + quantity : quantity);
        medicineRepository.save(medicine);
        return medicine.getStock();
    }

    @Override
    public Integer removeStock(Long id, Integer quantity) throws HMSUserException {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(()->new HMSUserException("MEDICINE_NOT_FOUND"));
        medicine.setStock(medicine.getStock() != null ? medicine.getStock() - quantity : 0);
        medicineRepository.save(medicine);
        return medicine.getStock();
    }   
}
