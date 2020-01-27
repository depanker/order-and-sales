package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.services.RevenueReportService;
import com.depanker.orderandsales.web.revenue.YTDRevenueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RevenueReportController {
    @Autowired
    RevenueReportService revenueReportServiceImpl;

    @GetMapping("/revenue-report/{company-name}")
    public List<YTDRevenueDTO> getRevenueReport(@PathVariable("company-name") String companyName) {
        return revenueReportServiceImpl.getRevenueReport(companyName);
    }
}
