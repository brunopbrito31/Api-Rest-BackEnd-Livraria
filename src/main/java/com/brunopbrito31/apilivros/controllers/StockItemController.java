package com.brunopbrito31.apilivros.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.brunopbrito31.apilivros.models.entities.StockItem;
import com.brunopbrito31.apilivros.services.StockItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<StockItem> createStock (@RequestParam("idproduct") Long idProduct){
        StockItem createdStock  = stockItemService.createStockItem(idProduct);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(createdStock.getId()).toUri();
        return ResponseEntity.created(uri).body(createdStock);  
    }

    @PutMapping("/add")
    public ResponseEntity<StockItem> addItemStock ( @RequestParam("idproduct") Long idProduct, @RequestParam("quantity")BigDecimal quantity ){
        StockItem oldStock  = stockItemService.addProductInStockItem(idProduct, quantity);
        return ResponseEntity.ok().body(oldStock);
    }

    @PutMapping("/rem")
    public ResponseEntity<StockItem> removeItemStock ( @RequestParam("idproduct") Long idProduct, @RequestParam("quantity") BigDecimal quantity){
        StockItem oldStock = stockItemService.removeProductInStockItem(idProduct, quantity);
        return ResponseEntity.ok().body(oldStock);
    }


}
