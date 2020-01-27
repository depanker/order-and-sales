package com.depanker.orderandsales.web.revenue;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter()
public class YTDRevenueDTO {
  private int year;
  private double total;
  private List<RevenueDTO> revenueDTOS;

   public  YTDRevenueDTO(List<RevenueDTO> revenueDTOS) {
       this.revenueDTOS  = revenueDTOS;
       total = revenueDTOS.stream().mapToDouble(revenueDTO -> revenueDTO.getRevenue()).sum();
       year =  revenueDTOS.stream().findFirst().map(revenueDTO -> revenueDTO.yearsNumber()).orElse(0);
   }

}
