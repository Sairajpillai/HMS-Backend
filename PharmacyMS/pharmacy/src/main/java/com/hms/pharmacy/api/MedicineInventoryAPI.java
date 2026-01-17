package com.hms.pharmacy.api;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.pharmacy.dto.MedicineInventoryDTO;
import com.hms.pharmacy.dto.ResponseDTO;
import com.hms.pharmacy.exception.HMSUserException;
import com.hms.pharmacy.service.MedicineInventoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@CrossOrigin
@RequestMapping("/pharmacy/inventory")
@RequiredArgsConstructor
public class MedicineInventoryAPI {
    private final MedicineInventoryService medicineInventoryService;

    @PostMapping("/add")
    public ResponseEntity<MedicineInventoryDTO> addMedicine(@RequestBody MedicineInventoryDTO medicineDTO) throws HMSUserException{
        MedicineInventoryDTO medicine = medicineInventoryService.addMedicine(medicineDTO);
        return new ResponseEntity<>(medicine,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineInventoryDTO> updateMedicine(@RequestBody MedicineInventoryDTO medicineDTO) throws HMSUserException{
        MedicineInventoryDTO medicine = medicineInventoryService.updateMedicine(medicineDTO);
        return new ResponseEntity<>(medicine,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MedicineInventoryDTO> getMedicineById(@PathVariable Long id) throws HMSUserException{
        MedicineInventoryDTO medicine = medicineInventoryService.getMedicineById(id);
        return new ResponseEntity<>(medicine,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineInventoryDTO>> getAllMedicines() throws HMSUserException {
        return new ResponseEntity<>(medicineInventoryService.getAllMedicines(),HttpStatus.OK);
    }   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> removeMedicine(@PathVariable Long id) throws HMSUserException{
        medicineInventoryService.deleteMedicine(id);
        return new ResponseEntity<>(new ResponseDTO("Medicine Deleted Successfully"),HttpStatus.OK);
    }
}
