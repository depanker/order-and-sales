package com.depanker.orderandsales.entities.company;

import com.depanker.orderandsales.entities.base.BaseEntity;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.entities.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"companyName"})})
public class Company extends BaseEntity {

    @NotNull
    @Valid
    @Embedded
    CompanyName companyName;

    @NotNull
    @Valid
    @Embedded
    CompanyFieldMapping fieldMapping;

    @OneToMany(mappedBy="company", fetch=FetchType.LAZY)
    List<Product> products;
    @OneToMany(mappedBy="company", fetch=FetchType.LAZY)
    List<Order> orders;

}
