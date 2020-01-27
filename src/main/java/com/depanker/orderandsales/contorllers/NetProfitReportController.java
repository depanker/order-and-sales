package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.services.NetProfitService;
import com.depanker.orderandsales.web.netprofit.YTDNetProfitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NetProfitReportController {

    @Autowired
    NetProfitService netProfitServiceImpl;


    @GetMapping("/net-profit-report/{company-name}")
    public List<YTDNetProfitDTO> getNetProfitReport(@PathVariable("company-name") String companyName) {
        return netProfitServiceImpl.getNetProfitReport(companyName);
    }
}
