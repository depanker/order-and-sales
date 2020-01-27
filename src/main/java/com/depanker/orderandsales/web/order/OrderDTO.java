package com.depanker.orderandsales.web.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class OrderDTO {
    Long orderNumber;
    @JsonFormat(pattern="MM/dd/yy")
    Date orderDate;
    Float total;
    List<LineItemDTO> lineItems;
}
