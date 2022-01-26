package com.brunopbrito31.apilivros.services;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Sale;
import com.brunopbrito31.apilivros.models.entities.SaleItem;
import com.brunopbrito31.apilivros.models.enums.Operation;
import com.brunopbrito31.apilivros.models.enums.SaleStatus;
import com.brunopbrito31.apilivros.repositories.SaleItemRepository;
import com.brunopbrito31.apilivros.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired 
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired 
    private StockItemService stockItemService;

    public Sale createSale(Long idClient){
        if(idClient != 0){
            Optional<Sale> oldSale =  saleRepository
            .getSaleByClientAndExpiration(idClient);
            
            if(oldSale.isPresent()){
               return oldSale.get(); 
            }
        }

        Sale sale = Sale.startSale(idClient);
        sale = saleRepository.save(sale);
        return sale;
    }


    public SaleItem addItemInSale(Long idSale, SaleItem saleItem){
        Optional<Sale> saleSearched = saleRepository.findById(idSale);
        SaleItem result;
        if(!saleSearched.isPresent()){
            throw new IllegalArgumentException("Unexpected Error");
        }
        Optional<SaleItem> oldSaleItem = saleItemRepository.findSaleITemBySaleAndProduct(
            idSale, 
            saleItem.getProduct().getId()
        );
        // Update a old Sale Item
        if(oldSaleItem.isPresent()){
            SaleItem oldConfirmedSaleItem = oldSaleItem.get();
            oldConfirmedSaleItem.updateQuantity(
                saleItem.getQuantity(), 
                Operation.ADD
            );
            result = saleItemRepository.save(oldConfirmedSaleItem);

        // Save a new Sale Item
        }else{
            saleItem.setFatherSale(saleSearched.get());
            result = saleItemRepository.save(saleItem);
            Sale saleConfirm = saleSearched.get();
            if(saleConfirm.getStatus() == SaleStatus.STARTED){
                saleConfirm.setStatus(SaleStatus.INPROGRESS);
                saleRepository.save(saleConfirm);
            }
        }        
        // Remove product from stock
        stockItemService.removeProductInStockItem(
            saleItem.getProduct().getId(),
            saleItem.getQuantity()
        );

        return result;
    }


}
