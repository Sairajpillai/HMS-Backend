package com.hms.pharmacy.dto;

import java.time.LocalDate;

import com.hms.pharmacy.entity.Medicine;
import com.hms.pharmacy.entity.MedicineInventory;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineInventoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long medicineId;
    private String batchNo;
    private Integer quantity;
    private LocalDate expiryDate;
    private LocalDate addedDate;
    private Integer initialQuantity;
    private StockStatus status;

    public MedicineInventory toEntity(){
        return new MedicineInventory(id, new Medicine(medicineId), batchNo, quantity, expiryDate, addedDate,initialQuantity,status);
    } 
}
