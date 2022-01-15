package com.brunopbrito31.apilivros.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.models.entities.StockItem;
import com.brunopbrito31.apilivros.repositories.ProductRepository;
import com.brunopbrito31.apilivros.repositories.StockItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockItemService {
    
    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<StockItem> getStockItems(){
        return stockItemRepository.findAll();
    }

    public Optional<StockItem> getStockItemById(Long id){
        Optional<StockItem> item = stockItemRepository.findById(id);
        return item;
    }

    public Optional<StockItem> getStockItemByProductId(Long id){

        Optional<Product> productSearched = productRepository.findById(id);
        if(!productSearched.isPresent()){
            throw new IllegalArgumentException("Product not exists");
        }

        Optional<StockItem> stockItemSearched = stockItemRepository.getStockItemByProduct(productSearched.get());

        return stockItemSearched;
    }

    public StockItem saveStockItem (StockItem stockItem) {
        stockItem = stockItemRepository.save(stockItem);
        return stockItem;
    }

    public String deleteStockItemById (Long id){
        try{
            stockItemRepository.deleteById(id);
            return "Produto Excluido com Sucesso!";
        }catch(RuntimeException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String deleteStockItem(StockItem stockItem){
        try{
            stockItemRepository.delete(stockItem);
            return "Producto Excluido com Sucesso";
        }catch(RuntimeException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public StockItem updateStockItem(BigDecimal quantity, Long idProduct){
        Optional<Product> productSearched = productRepository.findById(idProduct);
        if(!productSearched.isPresent()){
            throw new IllegalArgumentException("Product not found!");
        }
        Optional<StockItem> stockItemSearched = stockItemRepository.getStockItemByProduct(productSearched.get());

        // Caso o Estoque Não Exista
        if(!stockItemSearched.isPresent() ){
            if(quantity.compareTo(BigDecimal.valueOf(0)) > 0 ){
                StockItem newStockItem = new StockItem();
                newStockItem.setProduct(productSearched.get());
                newStockItem.setQuantity(quantity);
                newStockItem = stockItemRepository.save(newStockItem);
                return newStockItem;

            // Caso tente retirar do stock vazio
            }else{
                throw new IllegalArgumentException("Stock not exists!");
            }

        // Caso o Estoque já Exista
        }else{
            StockItem oldStockItem = stockItemSearched.get();
            if(quantity.compareTo(BigDecimal.valueOf(0)) > 0 ){
                BigDecimal newQuantity = oldStockItem.getQuantity().add(quantity);
                oldStockItem.setQuantity(newQuantity);
                oldStockItem = stockItemRepository.save(oldStockItem);
                return oldStockItem;

            }else if (quantity.compareTo(BigDecimal.valueOf(0)) == 0 ){
                oldStockItem.setQuantity(quantity);
                oldStockItem = stockItemRepository.save(oldStockItem);
                return oldStockItem;

            }else{
                if(quantity.compareTo(oldStockItem.getQuantity()) > 0){
                    throw new IllegalArgumentException("No have Stock Item Suficient");
                    
                }else{
                    BigDecimal newQuantity = oldStockItem.getQuantity().add(quantity);
                    oldStockItem.setQuantity(newQuantity);
                    oldStockItem = stockItemRepository.save(oldStockItem);
                    return oldStockItem;
                }
            }
        }

    }
}
