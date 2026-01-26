package com.hms.pharmacy.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.exception.HMSUserException;

@Repository
public interface SaleItemService {
    Long createSaleItem(SaleItemDTO saleItemDTO) throws HMSUserException;
    void createMultipleSaleItem(Long saleId,Long medicineId,List<SaleItemDTO> saleItemDTOs) throws HMSUserException;
    void updateSaleItem(SaleItemDTO saleItemDTO) throws HMSUserException;
    List<SaleItemDTO> getItemsBySaleId(Long saleId) throws HMSUserException;
    SaleItemDTO getSaleItem(Long id) throws HMSUserException;
}
