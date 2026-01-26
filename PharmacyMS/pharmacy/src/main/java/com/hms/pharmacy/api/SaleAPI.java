package com.hms.pharmacy.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.pharmacy.dto.ResponseDTO;
import com.hms.pharmacy.dto.SaleDTO;
import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.exception.HMSUserException;
import com.hms.pharmacy.service.SaleItemService;
import com.hms.pharmacy.service.SaleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
@RequestMapping("/pharmacy/sales")
@RequiredArgsConstructor
public class SaleAPI {
    private final SaleService saleService;
    private final SaleItemService saleItemService;

    @PostMapping("/create")
    public ResponseEntity<Long> createSale (@RequestBody SaleDTO saleDTO) throws HMSUserException {
        return new ResponseEntity<>(saleService.createSale(saleDTO),HttpStatus.CREATED);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateSale(@RequestBody SaleDTO saleDTO) throws HMSUserException{
        saleService.updateSale(saleDTO);
        return new ResponseEntity<>(new ResponseDTO("Sale Updated Successfully"),HttpStatus.OK);
    }

    @GetMapping("/getSaleItems/{saleId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItems(@PathVariable Long saleId) throws HMSUserException{
        return new ResponseEntity<>(saleItemService.getItemsBySaleId(saleId),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) throws HMSUserException{
        return new ResponseEntity<>(saleService.getSale(id),HttpStatus.OK);
    }


}
