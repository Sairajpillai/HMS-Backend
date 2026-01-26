package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleDTO;
import com.hms.pharmacy.exception.HMSUserException;

public interface SaleService {
    Long createSale(SaleDTO saleDTO) throws HMSUserException;
    void updateSale(SaleDTO saleDTO) throws HMSUserException;
    SaleDTO getSale(Long id) throws HMSUserException;
    SaleDTO getSaleByPrescriptionId(Long prescriptionId) throws HMSUserException;
}
