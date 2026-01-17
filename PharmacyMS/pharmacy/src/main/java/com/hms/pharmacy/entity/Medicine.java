package com.hms.pharmacy.entity;

import java.time.LocalDateTime;

import com.hms.pharmacy.dto.MedicineDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    public Medicine(Long medicineId) {
        this.id = medicineId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String dosage;
    private MedicineCategory category;
    private MedicineType type;
    private String manufacturer;
    private Integer unitPrice;
    private Integer stock;
    private LocalDateTime createdAt;

    public MedicineDTO toDTO(){
        return new MedicineDTO(id, name, dosage, category, type, manufacturer, unitPrice,stock, createdAt);
    }
}
