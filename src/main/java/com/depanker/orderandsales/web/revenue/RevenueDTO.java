package com.depanker.orderandsales.web.revenue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RevenueDTO {

    private String month;
    private Double revenue;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer year;

    public RevenueDTO(int year, int month, Double revenue) {
        this.month = year+"-"+month;
        this.year = year;
        this.revenue =   Math.round(revenue * 100.0) / 100.0;
    }

    public int yearsNumber() {
        return year;
    }
}
