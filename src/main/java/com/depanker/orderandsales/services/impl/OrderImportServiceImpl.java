package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.bean.OrderAdapter;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.repositories.OrderRepository;
import com.depanker.orderandsales.services.CurrencyService;
import com.depanker.orderandsales.services.OrderAdapterService;
import com.depanker.orderandsales.services.OrderImportService;
import com.depanker.orderandsales.web.currency.ExchangeRates;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
@Service
public class OrderImportServiceImpl implements OrderImportService {

    @Autowired
    OrderAdapterService  orderAdapterServiceImpl;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CurrencyService currencyServiceImpl;

    @Override
    public void importOrder(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<OrderAdapter> orderAdapters = new CsvToBeanBuilder(reader)
                    .withType(OrderAdapter.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<Order> orders =  new ArrayList<>();
            Order currentOrder = null;
            ExchangeRates exchangeRates = currencyServiceImpl.getExchangeRates();
            for (OrderAdapter orderAdapter : orderAdapters) {
                if (orderAdapter.getCompanyName() != null && !orderAdapter.getCompanyName().isEmpty()) {
                    currentOrder = orderAdapterServiceImpl.map(orderAdapter, exchangeRates);
                    orders.add(currentOrder);
                } else {
                    currentOrder =  orderAdapterServiceImpl.addLineItem(currentOrder, orderAdapter, exchangeRates);
                }
            }
            orderRepository.saveAll(orders);
        }
    }
}
