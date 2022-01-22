package com.brunopbrito31.apilivros.services;

import com.brunopbrito31.apilivros.models.entities.Sale;
import com.brunopbrito31.apilivros.models.enums.SaleStatus;
import com.brunopbrito31.apilivros.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired 
    private SaleRepository saleRepository;


}
