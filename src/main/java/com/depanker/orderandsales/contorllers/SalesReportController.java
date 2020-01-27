package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.services.SalesReportService;
import com.depanker.orderandsales.web.exception.NoContentException;
import com.depanker.orderandsales.web.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalesReportController {
    @Autowired
    SalesReportService salesReportServiceImpl;

    @GetMapping("/sales-report/{company-name}")
    public List<OrderDTO> getReport(@PathVariable("company-name") String companyName) throws NoContentException {
        return salesReportServiceImpl.getReport(companyName);
    }
}

