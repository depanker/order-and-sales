package com.depanker.orderandsales.entities.order;

import com.depanker.orderandsales.entities.base.BaseEntity;
import com.depanker.orderandsales.entities.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Embeddable
@Setter
@Getter
public class LineItem extends BaseEntity {
    @ManyToOne
    private Product product;

    private Integer quantity;

    @Column(nullable = false)
    private Float salesPrice;
}
