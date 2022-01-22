package com.brunopbrito31.apilivros.models.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.brunopbrito31.apilivros.models.enums.SaleStatus;
import com.brunopbrito31.apilivros.models.tools.Auxiliar;

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

    public static Sale startSale() {
        Sale newSale = new Sale();
        newSale.status = SaleStatus.STARTED;
        return newSale;
    }

    public void addItem(SaleItem item) {
        Auxiliar.verifyPositiveNonNullNumberIntegrity(item.getQuantity());
        
        if(this.status.equals(SaleStatus.STARTED)){
            this.status = SaleStatus.INPROGRESS;
        }else if (!this.status.equals(SaleStatus.INPROGRESS)){
            throw new IllegalArgumentException("Sale is not start");
        }

        if(!insertInOldSaleItem(item)){
            this.saleItems.add(item);
        }
    }

    public void removeItem(SaleItem item){
        Auxiliar.verifyPositiveNumberIntegrity(item.getQuantity());

        if(!this.status.equals(SaleStatus.INPROGRESS)){
            
        }
    }

    private Boolean insertInOldSaleItem(SaleItem item){
        // Verify if product exists in sale
        Stream<SaleItem> saleItemFilter = this.saleItems.stream()
                                        .filter(x -> x.getProduct().getId().equals(item.getProduct().getId()));

        List<SaleItem> saleItensWithFilter = saleItemFilter.collect(Collectors.toList());

        if(!saleItensWithFilter.isEmpty()){
            saleItemFilter.forEach(x -> x.updateQuantity(item.getQuantity()));
            return true;
        }
        return false;
    }
}
