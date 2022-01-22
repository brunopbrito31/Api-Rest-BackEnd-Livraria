package com.brunopbrito31.apilivros.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.brunopbrito31.apilivros.models.enums.Operation;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale fatherSale;

    public void updateQuantity(BigDecimal quantity, Operation operation) {
        if (operation.equals(Operation.REM)){
            if( quantity.compareTo(this.getQuantity()) > 0){
                throw new IllegalArgumentException("No have a quantity of products");
            }
            quantity = quantity.multiply(BigDecimal.valueOf(-1));
        }else if (!operation.equals(Operation.ADD)){
            throw new IllegalArgumentException("Invalid Operation");
        }
        this.quantity.add(quantity);
        this.totalPrice = this.unitPrice.multiply(this.quantity);
    }
    
}
