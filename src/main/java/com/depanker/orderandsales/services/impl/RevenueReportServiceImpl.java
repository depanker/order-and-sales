package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.services.RevenueReportService;
import com.depanker.orderandsales.web.revenue.RevenueDTO;
import com.depanker.orderandsales.web.revenue.YTDRevenueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class RevenueReportServiceImpl implements RevenueReportService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<YTDRevenueDTO> getRevenueReport(String companyName) {
        List<RevenueDTO> data = companyRepository.findByCompanyName(new CompanyName(companyName))
                .map(company -> orderRepository.getRevenues(company))
                .orElseThrow(() ->  new ResourceNotFoundException("No record found for the company "+companyName));
        if (data == null || data.isEmpty()) {
            throw new ResourceNotFoundException("No sales data found for the company "+companyName);

        }
        Map<Integer, List<RevenueDTO>> map = data.stream().collect(Collectors.groupingBy(RevenueDTO::yearsNumber));
        return  map.values().stream().map(revenueDTOS -> new YTDRevenueDTO(revenueDTOS)).collect(Collectors.toList());
    }
}
