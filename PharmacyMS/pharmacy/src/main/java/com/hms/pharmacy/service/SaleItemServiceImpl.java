package com.hms.pharmacy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.entity.SaleItem;
import com.hms.pharmacy.exception.HMSUserException;
import com.hms.pharmacy.repository.SaleItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;

    @Override
    public Long createSaleItem(SaleItemDTO saleItemDTO) throws HMSUserException {
        return saleItemRepository.save(saleItemDTO.toEntity()).getId();
    }

    @Override
    public void createMultipleSaleItem(Long saleId, Long medicineId, List<SaleItemDTO> saleItemDTOs)
            throws HMSUserException {
        saleItemDTOs.stream().map((x) -> {
            x.setSaleId(saleId);
            x.setMedicineId(medicineId);
            return x.toEntity();
        }).forEach(saleItemRepository::save);
    }

    @Override
    public void updateSaleItem(SaleItemDTO saleItemDTO) throws HMSUserException {
        SaleItem existingSaleItem = saleItemRepository.findById(saleItemDTO.getId()).orElseThrow(()-> new HMSUserException("SALE_ITEM_NOT_FOUND"));
        existingSaleItem.setQuantity(saleItemDTO.getQuantity());
        existingSaleItem.setUnitPrice(saleItemDTO.getUnitPrice());
        saleItemRepository.save(existingSaleItem);
    }

    @Override
    public List<SaleItemDTO> getItemsBySaleId(Long saleId) throws HMSUserException {
        return saleItemRepository.findBySaleId(saleId).stream().map(SaleItem::toDTO).toList();
    }

    @Override
    public SaleItemDTO getSaleItem(Long id) throws HMSUserException {
        return saleItemRepository.findById(id).map(SaleItem::toDTO).orElseThrow(() -> new HMSUserException("SALE_ITEM_NOT_FOUND"));
    }

}
