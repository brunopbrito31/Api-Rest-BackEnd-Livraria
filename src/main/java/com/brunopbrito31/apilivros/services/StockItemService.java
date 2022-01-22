package com.brunopbrito31.apilivros.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.models.entities.StockItem;
import com.brunopbrito31.apilivros.models.tools.Auxiliar;
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

    public StockItem createStockItem( Long idProduct ){
        // Carrega as informações do produto com validação da sua existencia na base
        Product searchedProduct = getProductByIdWithFilter(idProduct);

        // Verifica se já há estoque para o produto buscado
        Optional<StockItem> oldStockItem = stockItemRepository.getStockItemByProduct(searchedProduct);

        if(oldStockItem.isPresent()){
            return oldStockItem.get();
        }

        // Estoque ainda não existe
        StockItem stockItem = new StockItem();
        stockItem.setProduct(searchedProduct);
        stockItem.setQuantity(BigDecimal.valueOf(0));
        stockItem = stockItemRepository.save(stockItem);
        return stockItem;
    }

    public StockItem addProductInStockItem( Long idProduct, BigDecimal quantity ) {
        Auxiliar.verifyPositiveNumberIntegrity(quantity);
        Product searchedProduct = getProductByIdWithFilter(idProduct);

        Optional<StockItem> searchedStockItem = stockItemRepository.getStockItemByProduct(searchedProduct);

        if(searchedStockItem.isPresent()) {
            StockItem oldStockItem = searchedStockItem.get();
            BigDecimal newQuantity = oldStockItem.getQuantity().add(quantity);
            oldStockItem.setQuantity(newQuantity);
            oldStockItem = stockItemRepository.save(oldStockItem);
            return oldStockItem;
        }else{
            StockItem newStockItem = createStockItem(idProduct);
            newStockItem.setQuantity(quantity);
            newStockItem = stockItemRepository.save(newStockItem);
            return newStockItem;
        }
    }

    public StockItem removeProductInStockItem( Long idProduct, BigDecimal quantity ) {
        Auxiliar.verifyPositiveNumberIntegrity(quantity);
        Product searchedProduct = getProductByIdWithFilter(idProduct);
        Optional<StockItem> searchedStockItem = stockItemRepository.getStockItemByProduct(searchedProduct);

        if(!searchedStockItem.isPresent()) {
            throw new IllegalArgumentException("Product not is present in stock item");
        }

        StockItem oldStockItem = searchedStockItem.get();

        if(oldStockItem.getQuantity().compareTo(quantity) < 0) {
            throw new IllegalArgumentException("No have a quantity of products in stock");
        }

        BigDecimal newQuantity = oldStockItem.getQuantity().add(BigDecimal.valueOf(-1).multiply(quantity));
        oldStockItem.setQuantity(newQuantity);
        oldStockItem = stockItemRepository.save(oldStockItem);
        return oldStockItem;
    }

    private Product getProductByIdWithFilter(Long idProduct){
        Optional<Product> searchedProduct = productRepository.findById(idProduct);
        if( !searchedProduct.isPresent() ){
            throw new IllegalArgumentException("Product Invalid");
        }
        return searchedProduct.get();
    }

    private StockItem getStockItemByIdWithFilter(Long idStockItem){
        Optional<StockItem> searchedStockItem = stockItemRepository.findById(idStockItem);
        if( !searchedStockItem.isPresent() ){
            throw new IllegalArgumentException("Stock Invalid");
        }
        return searchedStockItem.get();
    }
}
