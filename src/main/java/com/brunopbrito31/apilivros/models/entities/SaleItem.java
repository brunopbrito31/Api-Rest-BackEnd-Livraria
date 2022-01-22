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

    public void updateQuantity(BigDecimal quantity) {
        this.quantity.add(quantity);
        this.totalPrice = this.totalPrice.add(this.unitPrice.multiply(quantity));
    }
    
}
