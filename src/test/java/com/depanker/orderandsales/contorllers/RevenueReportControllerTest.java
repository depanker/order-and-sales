package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.BaseTest;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.web.revenue.RevenueDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class RevenueReportControllerTest extends BaseTest {

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    CompanyRepository companyRepository;

    @Test
    void getRevenueReport() throws Exception {
        when(companyRepository.findByCompanyName(any(CompanyName.class)))
                .thenReturn(Optional.of(getMockCompany()));
        when(orderRepository.getRevenues(any(Company.class)))
                .thenReturn(Arrays.asList(new RevenueDTO(2019, 1, 99.9)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/revenue-report/A"))
                .andExpect(status().isOk());
    }

    @Test
    void getRevenueForUnregisteredCompany() throws Exception {
        when(companyRepository.findByCompanyName(any(CompanyName.class)))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/revenue-report/A"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRevenueReportWhenNotDataisPresent() throws Exception {
        when(companyRepository.findByCompanyName(any(CompanyName.class)))
                .thenReturn(Optional.of(getMockCompany()));
        when(orderRepository.getRevenues(any(Company.class)))
                .thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/revenue-report/A"))
                .andExpect(status().isNotFound());
    }
}