package com.depanker.orderandsales.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderAdapter {
    @CsvBindByName(column = "Company")
    private String companyName;

    @CsvBindByName(column = "Order date")
    @CsvDate("MM/dd/yy")
    private Date orderDate;

    @CsvBindByName(column = "Order number")
    private Long companyOrderNumber;

    @CsvBindByName(column = "Product Id", required = true)
    private Long companyProductId;

    @CsvBindByName(column = "Quantity", required = true)
    private Integer quantity;

    @CsvBindByName(column = "Sale price", required = true)
    private Float salesPrice;

    @CsvBindByName(column = "Currency", required = true)
    private String currency;
}
