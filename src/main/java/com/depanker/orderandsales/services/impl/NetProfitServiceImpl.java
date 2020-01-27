package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.repositories.ProductRepository;
import com.depanker.orderandsales.services.NetProfitService;
import com.depanker.orderandsales.web.netprofit.YTDNetProfitDTO;
import com.depanker.orderandsales.web.revenue.RevenueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class NetProfitServiceImpl implements NetProfitService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<YTDNetProfitDTO> getNetProfitReport(String companyName) {
        return getYtdRevenueDTOS(companyName, companyRepository, orderRepository);
    }

    private List<YTDNetProfitDTO> getYtdRevenueDTOS(String companyName, CompanyRepository companyRepository, OrderRepository orderRepository) {
        Optional<Company> companyData = companyRepository.findByCompanyName(new CompanyName(companyName));
        List<RevenueDTO> data = companyData
                .map(company -> orderRepository.getRevenues(company))
                .orElseThrow(() ->  new ResourceNotFoundException("No record found for the company "+companyName));
        Double totalCost =  productRepository.getTotalCost(companyData.get());
        if (data == null || data.isEmpty()) {
            throw new ResourceNotFoundException("No sales data found for the company "+companyName);

        }
        Map<Integer, List<RevenueDTO>> map = data.stream().collect(Collectors.groupingBy(RevenueDTO::yearsNumber));
        return  map.values().stream().map(revenueDTOS -> new YTDNetProfitDTO(revenueDTOS, totalCost)).collect(Collectors.toList());
    }
}
