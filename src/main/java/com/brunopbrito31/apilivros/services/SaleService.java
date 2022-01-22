package com.brunopbrito31.apilivros.services;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Sale;
import com.brunopbrito31.apilivros.models.entities.SaleItem;
import com.brunopbrito31.apilivros.models.entities.StockItem;
import com.brunopbrito31.apilivros.models.enums.SaleStatus;
import com.brunopbrito31.apilivros.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired 
    private SaleRepository saleRepository;

    @Autowired 
    private StockItemService stockItemService;


    public Sale createSale(){
        Sale sale = Sale.startSale();
        sale = saleRepository.save(sale);
        return sale;
    }


    // Editions is needed

    // NOTES: End is need
    public Sale updateSaleinBase(Sale sale){
        return null;
    }

    public Sale addItemInSale(Sale sale, SaleItem saleItem){
        // Remove product from stock
        stockItemService.removeProductInStockItem(
            saleItem.getProduct().getId(),
            saleItem.getQuantity()
        );
        // Insert in sale
        // Sale item persistis before or after sale update
        sale.addItem(saleItem);
        return updateSaleinBase(sale);
    }


}
