package com.depanker.orderandsales.services;

import com.depanker.orderandsales.web.netprofit.YTDNetProfitDTO;

import java.util.List;

public interface NetProfitService {
    List<YTDNetProfitDTO> getNetProfitReport(String companyName);
}
