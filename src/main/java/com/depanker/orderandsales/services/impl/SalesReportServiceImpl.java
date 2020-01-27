package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.order.LineItem;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.services.SalesReportService;
import com.depanker.orderandsales.web.exception.NoContentException;
import com.depanker.orderandsales.web.order.LineItemDTO;
import com.depanker.orderandsales.web.order.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SalesReportServiceImpl implements SalesReportService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<OrderDTO> getReport(String companyName) throws NoContentException {
        List<Order> orders = orderRepository.findByCompanyCompanyName(new CompanyName(companyName));
        if (orders == null || orders.isEmpty()) {
            throw new NoContentException("Could not find company with name "+companyName);
        }
        List<OrderDTO> orderDTOS =  orders.stream().map(order -> mapOrder(order))
                .collect(Collectors.toList());
        return orderDTOS;
    }

    private OrderDTO mapOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        modelMapper.map(order, orderDTO);
        orderDTO.setOrderNumber(order.getOrderNumber());
        List<LineItem> lineItems =  order.getLineItems();
        List<LineItemDTO> lineItemDTOS = lineItems.stream()
                .map(lineItem -> {
                    LineItemDTO lineItemDTO =  new LineItemDTO();
                    modelMapper.map(lineItem, lineItemDTO);
                    lineItemDTO.setProduct(lineItem.getProduct().getDescription());
                    return lineItemDTO;
                }).collect(Collectors.toList());
        orderDTO.setLineItems(lineItemDTOS);
        return orderDTO;
    }
}
