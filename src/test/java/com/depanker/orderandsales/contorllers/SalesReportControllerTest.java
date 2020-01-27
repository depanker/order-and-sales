package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.BaseTest;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.order.LineItem;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SalesReportControllerTest  extends BaseTest {

    @MockBean
    OrderRepository orderRepository;

    @Test
    void getReport() throws Exception {
        List<Order> mockOrders = new ArrayList<>();
        when(orderRepository.findByCompanyCompanyName(any(CompanyName.class))).thenReturn(Arrays.asList(getMockOrder()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sales-report/A"))
                .andExpect(status().isOk());
    }

    @Test
    void getNoData() throws Exception {
        List<Order> mockOrders = new ArrayList<>();
        when(orderRepository.findByCompanyCompanyName(any(CompanyName.class))).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sales-report/A"))
                .andExpect(status().isNotFound());
    }

    private Order getMockOrder() {
        Order result =  new Order();
        result.setCompany(getMockCompany());
        result.setCurrency("EUR");
        LineItem lineItem =  new LineItem();
        lineItem.setQuantity(1);
        lineItem.setSalesPrice(101.0f);
        lineItem.setProduct(getMockProduct());
        result.setLineItems(Arrays.asList(lineItem));
        result.setOrderDate(new Date());
        result.setOrderNumber(1l);
        result.setTotal(101.0f);
        return result;
    }
}