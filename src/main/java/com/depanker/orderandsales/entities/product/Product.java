package com.depanker.orderandsales.entities.product;

import com.depanker.orderandsales.entities.base.BaseEntity;
import com.depanker.orderandsales.entities.company.Company;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"productCompanyId", "company_id"})})
@Embeddable
public class Product extends BaseEntity {
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    String description;

    @NotNull
    @Min(1)
    Long productCompanyId;

    @NotNull
    @Min(0)
    @Column(precision = 11, scale = 2)
    Float cost;

    @NotNull
    String currency;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    Company company;


}
