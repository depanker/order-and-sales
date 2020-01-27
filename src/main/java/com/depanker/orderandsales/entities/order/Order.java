package com.depanker.orderandsales.entities.order;

import com.depanker.orderandsales.entities.base.BaseEntity;
import com.depanker.orderandsales.entities.company.Company;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sales_order",uniqueConstraints = {@UniqueConstraint(columnNames = {"orderNumber", "company_id"})})
public class Order extends BaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<LineItem> lineItems = new ArrayList<>();

    private Float total;

    @Column(length = 3)
    private String currency;

    private Date orderDate;

    private Long orderNumber;

    @PrePersist
    public void calculateTotal(){
        total =  lineItems.stream()
                .map(lineItem -> lineItem.getSalesPrice())
                .reduce((a,b) -> a+b).get();
    }

    public void addLinItem(LineItem lineItem){
        this.lineItems.add(lineItem);
    }
}
