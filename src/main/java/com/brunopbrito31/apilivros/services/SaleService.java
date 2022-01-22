package com.brunopbrito31.apilivros.services;

import com.brunopbrito31.apilivros.models.entities.Sale;
import com.brunopbrito31.apilivros.models.enums.SaleStatus;
import com.brunopbrito31.apilivros.repositories.SaleRepository;

import org.springframework.stereotype.Service;

@Service
public class SaleService {
    
    private Sale sale;
    private SaleRepository saleRepository;

    public Sale saleStart () {
        if(
            sale.equals(null) || 
            sale.getStatus().equals(SaleStatus.CANCELED) || 
            sale.getStatus().equals(SaleStatus.FINISH)
        ){
            this.sale = saleRepository.save(new Sale());
        }
        return this.sale;
    }

}
