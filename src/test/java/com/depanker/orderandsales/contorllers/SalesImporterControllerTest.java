package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.BaseTest;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.entities.product.Product;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SalesImporterControllerTest  extends BaseTest {

    @MockBean
    CompanyRepository companyRepository;
    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    void uploadFile() throws Exception {

        Optional<Company> company = Optional.of(getMockCompany());
        when(companyRepository.findByCompanyName(any(CompanyName.class))).thenReturn(company);
        List<Product> products = new ArrayList<>();

        when(productRepository.findByCompanyAndProductCompanyId(any(Company.class), any(Long.class)))
                .thenReturn(Optional.of(getMockProduct()));
        when(orderRepository.saveAll(any(List.class))).thenReturn(new ArrayList<Order>());
        MediaType mediaType = mediaType();
        MockMultipartFile multipartFile = mockMultipartFile("salesFile", "Sales.csv");
        mockMvc.perform(
                MockMvcRequestBuilders.fileUpload("/upload-order")
                        .file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void uploadFileInvlaidInput() throws Exception {
        MediaType mediaType = mediaType();
        MockMultipartFile multipartFile = mockMultipartFile("someFile", "Sales.csv");
        mockMvc.perform(
                MockMvcRequestBuilders.fileUpload("/upload-order")
                        .file(multipartFile))
                .andExpect(status().isBadRequest());
    }
}