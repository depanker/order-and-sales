package com.depanker.orderandsales.services;

import com.depanker.orderandsales.web.revenue.YTDRevenueDTO;

import java.util.List;

public interface RevenueReportService {
    List<YTDRevenueDTO> getRevenueReport(String companyName);
}
