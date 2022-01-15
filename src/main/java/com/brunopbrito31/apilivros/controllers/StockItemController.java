package com.brunopbrito31.apilivros.controllers;

import java.util.List;

import com.brunopbrito31.apilivros.models.entities.StockItem;
import com.brunopbrito31.apilivros.services.StockItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-items")
public class StockItemController {
    
    @Autowired
    private StockItemService stockItemService;

    @GetMapping
    public ResponseEntity<List<StockItem>> getAll(){
        List<StockItem> items = stockItemService.getStockItems();
        if(items.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok().body(items);
        }
    }

    @PostMapping
    public ResponseEntity<StockItem> insert(StockItem item){
        return null;
    }


}
