package com.brunopbrito31.apilivros.models.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
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

import com.brunopbrito31.apilivros.models.enums.Operation;
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

    private Date expiration;

    @OneToMany(mappedBy = "fatherSale", fetch = FetchType.LAZY)
    private List<SaleItem> saleItems;

    private BigDecimal total;

    private Long idUser;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;


    // Ok - Done
    public static Sale startSale(Long idClient) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(Instant.now()));
        cal.add(Calendar.DAY_OF_MONTH,2);

        Sale newSale = new Sale();
        newSale.setDate(Date.from(Instant.now()));
        newSale.setExpiration(cal.getTime());
        newSale.setStatus(SaleStatus.STARTED);
        if(idClient != 0){
            newSale.setId(idClient);
        }
        return newSale;
    }

    public void addItem(SaleItem item) {
        Auxiliar.verifyPositiveNonNullNumberIntegrity(item.getQuantity());
    
        if(this.status.equals(SaleStatus.STARTED)){
            this.status = SaleStatus.INPROGRESS;

        }else if (!this.status.equals(SaleStatus.INPROGRESS)){
            throw new IllegalArgumentException("Sale is not start");
        }

        if(!updateOldSaleItem(item, Operation.ADD)){
            this.saleItems.add(item);
        }
    }

    public void removeItem(SaleItem item){
        Auxiliar.verifyPositiveNumberIntegrity(item.getQuantity());

        if(!this.status.equals(SaleStatus.INPROGRESS)){
            throw new IllegalArgumentException("No have a sale/product");
        }

        if(!updateOldSaleItem(item, Operation.REM)){
            throw new IllegalArgumentException("product not exists in sale");
        }
    }

    private Boolean updateOldSaleItem(SaleItem item, Operation operation){
        // Verify if product exists in sale
        Stream<SaleItem> saleItemFilter = this.saleItems.stream()
                                        .filter(x -> x.getProduct().getId().equals(item.getProduct().getId()));

        List<SaleItem> saleItensWithFilter = saleItemFilter.collect(Collectors.toList());

        if(!saleItensWithFilter.isEmpty()){
            saleItemFilter.forEach(x -> x.updateQuantity(item.getQuantity(),Operation.ADD));

            if(operation.equals(Operation.REM)){
                cleanNounsListItens();
            }
            return true;
        }
        return false;
    }

    // Excludes null itens from list
    private void cleanNounsListItens() {
        this.saleItems.removeIf(x -> x.getQuantity().equals(BigDecimal.valueOf(0)));
    }
}