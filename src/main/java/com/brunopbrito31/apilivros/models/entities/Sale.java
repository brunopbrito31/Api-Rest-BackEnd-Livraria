package com.brunopbrito31.apilivros.models.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.brunopbrito31.apilivros.models.enums.SaleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    
    @OneToMany(mappedBy = "fatherSale", fetch = FetchType.LAZY)
    private List<SaleItem> saleItems;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;
    
}
