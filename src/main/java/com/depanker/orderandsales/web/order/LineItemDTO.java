package com.depanker.orderandsales.web.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineItemDTO {
    String product;
    Integer quantity;
    Float salesPrice;
}
