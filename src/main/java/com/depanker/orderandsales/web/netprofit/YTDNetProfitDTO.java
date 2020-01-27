package com.depanker.orderandsales.web.netprofit;

import com.depanker.orderandsales.web.revenue.RevenueDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YTDNetProfitDTO {
    private int year;
    private double netProfit;
    private List<RevenueDTO> revenueDTOS;

    public  YTDNetProfitDTO(List<RevenueDTO> revenueDTOS, Double totalCost) {
        this.revenueDTOS  = revenueDTOS;
        double total = revenueDTOS.stream().mapToDouble(revenueDTO -> revenueDTO.getRevenue()).sum();
        netProfit = total - totalCost;
        year =  revenueDTOS.stream().findFirst().map(revenueDTO -> revenueDTO.yearsNumber()).orElse(0);
    }
}
